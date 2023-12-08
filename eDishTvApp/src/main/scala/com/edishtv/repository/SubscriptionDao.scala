package com.edishtv.repository

import scala.collection.mutable.ListBuffer
import org.slf4j.LoggerFactory
import java.sql.{Connection, Date, DriverManager, ResultSet, Statement, Timestamp}

import com.edishtv.model.{User, Channel, Subscription}


class SubscriptionDao {
  import com.edishtv.repository.SubscriptionDao
}


object SubscriptionDao {

  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost/edishtv_db"
  private val username = "root"
  private val password = "1234"

  private var connection: Connection = _
  private var statement: Statement = _

  private val logger = LoggerFactory.getLogger(classOf[SubscriptionDao])
  private var timestamp_now : Timestamp = _

  private def establishConnection(): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      statement = connection.createStatement()
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Failed to establish connection - $e")
      }
    }
  }

  def subscribe(user : User, channel : Channel, numberOfMonths : Int) : Boolean = {
    var isSuccess: Boolean = false
    try {
      establishConnection()

      val userId: Int = user.getUserId()
      val channelId: Int = channel.getChannelId()
      val cost: Int = channel.getMonthlySubscriptionFee()
      val totalNumberOfDays : Int = 30 * numberOfMonths
      //val currentDate : Date = ??
      //val expiryDate : Date =

      // Deleting all outdated subscriptions
      var query: String = "DELETE FROM subscription WHERE CURDATE() > expiry_date;"
      statement.executeUpdate(query)

      // Checking if the user has already subscribed to the channel
      query = "SELECT * FROM subscription " +
        s"WHERE user_id=$userId AND channel_id=$channelId AND " +
        "start_date <= CURDATE() AND CURDATE() <= expiry_date;"
      val resultSet : ResultSet = statement.executeQuery(query)
      if (resultSet.next())
        isSuccess = false
      else {
        //Inserting subscription record into 'subscription' table
        query = "INSERT INTO subscription (user_id, channel_id, cost, start_date, expiry_date) VALUES " +
          s"($userId, $channelId, $cost, CURDATE(), DATE_ADD(CURDATE(), INTERVAL $totalNumberOfDays DAY));"
        statement.executeUpdate(query)
        isSuccess = true
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while User (Id - ${user.getUserId()} tried " +
          s"subscribing to TV Channel (Id - ${channel.getChannelId()}, Number - ${channel.getChannelNumber()}) - $e")
      }
    }
    isSuccess
  }

  def unsubscribe(user : User, channelId : Int): Boolean = {
    var isSuccess : Boolean = false
    try {
      val userId: Int = user.getUserId()

      establishConnection()
      // Checking if user is already subscribed to the channel to unsubscribe
      var query: String = "SELECT * FROM subscription " +
        s"WHERE user_id=$userId AND channel_id=$channelId;"
      val resultSet: ResultSet = statement.executeQuery(query)
      if (resultSet.next()) {
        val subscriptionId : Int = resultSet.getInt("id")

        // Unsubscribing the channel
        query = s"DELETE FROM subscription WHERE id=$subscriptionId;"
        statement.executeUpdate(query)
        isSuccess = true
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while User (Id - ${user.getUserId()} tried " +
          s"Unsubscribing TV Channel (Id - $channelId) - $e")
      }
    }
    isSuccess
  }

  def getAllSubscription(user: User): ListBuffer[Subscription] = {
    val subscriptionList : ListBuffer[Subscription] = ListBuffer()
    try {
      val userId : Int = user.getUserId()
      var subscription : Subscription = null
      val query : String = "SELECT * FROM subscription " +
        s"WHERE user_id=$userId AND CURDATE() <= expiry_date;"

      establishConnection()
      val resultSet : ResultSet = statement.executeQuery(query)
      while (resultSet.next()) {
        val subscriptionId : Int = resultSet.getInt("id")
        val channelId : Int = resultSet.getInt("channel_id")
        val charge : Int = resultSet.getInt("cost")
        val startDate : Date = resultSet.getDate("start_date")
        val expiryDate: Date = resultSet.getDate("expiry_date")
        subscription = new Subscription(subscriptionId, userId, channelId, charge, startDate, expiryDate)
        subscriptionList += (subscription)
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while fetching all subscriptions for " +
          s"User (Id - ${user.getUserId()} - $e")
      }
    }
    subscriptionList
  }

}