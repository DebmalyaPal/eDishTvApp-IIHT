package com.edishtv.dao

import java.sql.{Connection, Date, DriverManager, ResultSet, Statement}
import scala.collection.mutable.ListBuffer
import com.edishtv.model.{Channel, Subscription, User}


class SubscriptionDao {
  import com.edishtv.dao.SubscriptionDao
}


object SubscriptionDao {

  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost/edishtv_db"
  private val username = "root"
  private val password = "1234"

  private var connection: Connection = _
  private var statement: Statement = _

  private def establishConnection(): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      statement = connection.createStatement()
    }
    catch {
      case e : Exception => println(e)
    }

  }

  def subscribe(user : User, channel : Channel): Boolean = {
    var isSuccess: Boolean = false
    try {
      establishConnection()

      val userId: Int = user.getUserId()
      val channelId: Int = channel.getChannelId()
      val cost: Int = channel.getMonthlySubscriptionFee()
      //val currentDate : Date = ??
      //val expiryDate : Date =

      // Checking if the user has already subscribed to the channel
      var query: String = s"SELECT * FROM subscription " +
        s"WHERE user_id=$userId AND channel_id=$channelId AND " +
        s"start_date >= CURDATE() AND CURDATE() <= expiry_date;"
      val resultSet: ResultSet = statement.executeQuery(query)
      if (resultSet.next())
        isSuccess = false
      else {
        // Deleting all outdated subscriptions
        query = s"DELETE FROM subscription WHERE CURDATE() > expiry_date;"
        statement.executeUpdate(query)

        //Inserting subscription record into 'subscription' table
        query = s"INSERT INTO subscription (user_id, channel_id, cost, start_date, expiry_date) VALUES " +
          s"($userId, $channelId, $cost, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY));"
        statement.executeUpdate(query)
        isSuccess = true
      }
    }
    catch {
      case e: Exception => println(e)
    }
    isSuccess
  }

  def unsubscribe(user : User, channelId : Int): Boolean = {
    var isSuccess: Boolean = false
    try {
      val userId: Int = user.getUserId()

      establishConnection()
      // Checking if user is already subscribed to the channel to unsubscribe
      var query: String = s"SELECT * FROM subscription " +
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
      case e: Exception => println(e)
    }
    isSuccess
  }

  def getAllSubscription(user: User): ListBuffer[Subscription] = {
    val subscriptionList : ListBuffer[Subscription] = ListBuffer()
    try {
      val userId : Int = user.getUserId()
      var subscription : Subscription = null
      val query : String = s"SELECT * FROM subscription " +
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
      case e : Exception => println(e)
    }
    subscriptionList
  }


}