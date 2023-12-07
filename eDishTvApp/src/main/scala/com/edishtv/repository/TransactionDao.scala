package com.edishtv.repository

import java.sql.{Connection, DriverManager, Statement, Timestamp}
import org.slf4j.LoggerFactory

import com.edishtv.model.{Channel, User}


class TransactionDao {
  import com.edishtv.repository.TransactionDao
}


object TransactionDao {

  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost/edishtv_db"
  private val username = "root"
  private val password = "1234"

  private var connection: Connection = _
  private var statement: Statement = _

  private val logger = LoggerFactory.getLogger(classOf[TransactionDao])
  private var timestamp_now: Timestamp = _

  private def establishConnection() : Unit = {
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

  def addTransaction(user : User, channel : Channel, numberOfMonths : Int) : Boolean = {
    var isSuccess: Boolean = false
    try {
      establishConnection()

      val userId: Int = user.getUserId()
      val channelId: Int = channel.getChannelId()
      val price: Int = channel.getMonthlySubscriptionFee()
      val totalNumberOfDays = 30 * numberOfMonths
      //val transaction : Date = ?? CURDATE()
      //val currentDate : Date = ?? CURDATE()
      //val expiryDate : Date = ??  DATE_ADD(CURDATE(), INTERVAL 30 DAY)

      // Adding to Transaction Record
      var query: String = s"INSERT INTO transaction " +
        s"(user_id, channel_id, price, transaction_date, start_date, expiry_date) VALUES " +
        s"($userId, $channelId, $price, CURDATE(), CURDATE(), DATE_ADD(CURDATE(), INTERVAL $totalNumberOfDays DAY));"
      statement.executeUpdate(query)
      isSuccess = true
    }
    catch {
      case e : Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} :  Error while storing " +
          s"Transaction record for User (Id - ${user.getUserId()} - $e")
      }
    }
    isSuccess
  }

}
