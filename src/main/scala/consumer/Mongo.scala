package com.baseman.consumer

import com.typesafe.config.{Config, ConfigFactory}
import org.mongodb.scala.{MongoClient, MongoDatabase}

object Mongo {
  private val config: Config = ConfigFactory.load(getClass.getClassLoader, "application.conf")

  private val dbConn: String = config.getString("mongo.conn")

  val client: MongoClient = MongoClient(dbConn)

  val db: MongoDatabase = client.getDatabase("eventTrack")
}