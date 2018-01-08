package com.baseman.consumer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink}
import com.typesafe.config.{Config, ConfigFactory}

object Consumer extends App {

  implicit val actorSystem = ActorSystem("actor-system")
  implicit val streamMaterializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher
  val log = actorSystem.log

  private val config: Config = ConfigFactory.load(getClass.getClassLoader, "application.conf")
  private val kafkaHost = config.getString("kafka.conn")
  private val kafkaGroup = config.getString("kafka.group")
  private val kafkaSubscription = Subscriptions.topics(config.getString("kafka.topic"))

  private val parallelism = config.getInt("mongo.parallelism")

  val consumerSettings = ConsumerSettings(
    system = actorSystem,
    keyDeserializer = new ByteArrayDeserializer,
    valueDeserializer = new StringDeserializer)
    .withBootstrapServers(kafkaHost)
    .withGroupId(kafkaGroup)
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val control = Consumer.plainSource(consumerSettings, kafkaSubscription)
    .map(consumerRecord => consumerRecord.value())
    .map(str => {
      log.error(str) //todo: remove log error
      str
    }).mapAsyncUnordered(parallelism)(strJson =>
      Mongo.db.getCollection("WebCompanionStats").insertOne(new Document(BsonDocument(strJson))).toFuture()
    ).toMat(Sink.ignore)(Keep.right).run()
}

//todo: define error handling?? Restart Subscription Strategy??