package com.edishtv.repository

import scala.collection.mutable.ListBuffer
import org.slf4j.LoggerFactory
import java.sql.{Connection, DriverManager, ResultSet, Statement, Timestamp}

import com.edishtv.model.Channel


class ChannelDao {
  import com.edishtv.repository.ChannelDao
}


object ChannelDao {

  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost/edishtv_db"
  private val username = "root"
  private val password = "1234"

  private var connection: Connection = _
  private var statement: Statement = _

  private val logger = LoggerFactory.getLogger(classOf[ChannelDao])
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

  def viewChannelList(): ListBuffer[Channel] = {
    var channelList : ListBuffer[Channel] = ListBuffer()
    var channel : Channel = null
    try {
      establishConnection()
      val query : String = s"SELECT * FROM channel;"
      val resultSet : ResultSet = statement.executeQuery(query)
      while (resultSet.next()) {
        val channelId : Int =
          resultSet.getInt("id")
        val channelName : String =
          resultSet.getString("name")
        val channelNumber : String =
          resultSet.getString("channel_number")
        val language : String =
          resultSet.getString("language")
        val description : String =
          resultSet.getString("description")
        val monthlySubscriptionFee : Int =
          resultSet.getInt("monthly_subscription_fee")
        channel = new Channel(
          channelId, channelName, language, description, channelNumber, monthlySubscriptionFee
        )
        channelList += (channel)
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error Viewing List of Channels - $e")
      }
    }
    channelList
  }

  def getChannelById(channelId : Int): Channel = {
    var channel : Channel = null
    try {
      establishConnection()
      val query = s"SELECT * FROM channel WHERE id=$channelId;"
      val resultSet : ResultSet = statement.executeQuery(query)
      if (resultSet.next()) {
        val channelName : String =
          resultSet.getString("name")
        val language : String =
          resultSet.getString("language")
        val description : String =
          resultSet.getString("description")
        val channelNumber : String =
          resultSet.getString("channel_number")
        val monthlySubscriptionFee : Int =
          resultSet.getInt("monthly_subscription_fee")
        channel = new Channel(
          channelId = channelId, channelName = channelName, language = language,
          description = description, channelNumber = channelNumber,
          monthlySubscriptionFee = monthlySubscriptionFee
        )
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error fetching Channel by Id - $e")
      }
    }
    channel
  }


  def addChannel(newChannel : Channel) : Channel = {
    var channel : Channel = null
    try {
      var doAlreadyExist: Boolean = false

      val channelName : String = newChannel.getChannelName()
      val channelNumber : String = newChannel.getChannelNumber()
      val language : String = newChannel.getLanguage()
      val description : String = newChannel.getDescription()
      val monthlySubscriptionFee : Int = newChannel.getMonthlySubscriptionFee()

      establishConnection()
      var query : String =
        s"SELECT * FROM channel WHERE channel_number='$channelNumber'"
      val resultSet : ResultSet = statement.executeQuery(query)
      if (resultSet.next()) doAlreadyExist = true

      if (!doAlreadyExist) {
        query = s"INSERT INTO channel (name, channel_number, language, description, monthly_subscription_fee) " +
            s"VALUES ('$channelName', '$channelNumber', '$language', '$description', $monthlySubscriptionFee);"
        statement.executeUpdate(query)
        channel = newChannel
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Adding New Channel - $e")
      }
    }
    channel
  }

  def updateChannel(updatedChannel : Channel) : Channel = {
    var channel : Channel = null
    try {
      var doAlreadyExist: Boolean = false

      val channelId : Int = updatedChannel.getChannelId()
      val updatedChannelName: String = updatedChannel.getChannelName()
      val updatedChannelNumber: String = updatedChannel.getChannelNumber()
      val updatedLanguage: String = updatedChannel.getLanguage()
      val updatedDescription: String = updatedChannel.getDescription()
      val updatedMonthlySubscriptionFee: Int = updatedChannel.getMonthlySubscriptionFee()

      establishConnection()
      var query: String = s"SELECT id, channel_number FROM channel WHERE id=$channelId;"
      val resultSet : ResultSet = statement.executeQuery(query)
      if (resultSet.next())
        doAlreadyExist = true

      if (doAlreadyExist) {

        var isNewChannelNumberSame = false
        var isNewChannelNumberFree = false

        if (resultSet.getString("channel_number").equals(updatedChannelNumber))
          isNewChannelNumberSame = true
        else {
          query = s"SELECT * FROM channel WHERE channel_number='$updatedChannelNumber';"
          val resultSet: ResultSet = statement.executeQuery(query)
          if (resultSet.next())
            isNewChannelNumberFree = false
          else
            isNewChannelNumberFree = true
        }

        if (isNewChannelNumberSame || (!isNewChannelNumberSame && isNewChannelNumberFree)) {
          query = s"UPDATE channel SET name='$updatedChannelName', channel_number='$updatedChannelNumber', " +
            s"language='$updatedLanguage', description='$updatedDescription', " +
            s"monthly_subscription_fee=$updatedMonthlySubscriptionFee WHERE id = $channelId;"
          statement.executeUpdate(query)
          channel = updatedChannel
        }
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Updating Channel " +
          s"(Id - ${updatedChannel.getChannelId()}, Number - ${updatedChannel.getChannelNumber()})  - $e")
      }
    }
    channel
  }

  def deleteChannel(channelId : Int) : Boolean = {
    var isSuccess: Boolean = false
    try {
      establishConnection()
      val query : String = s"DELETE FROM channel WHERE id=$channelId;"
      val recordsAffected : Int = statement.executeUpdate(query)
      if (recordsAffected == 1)
        isSuccess = true
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Deleting Channel (Id - $channelId)  - $e")
      }
    }
    isSuccess
  }

}