package com.edishtv.dao

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import com.edishtv.model.{User, Wallet}


class WalletDao {
  import com.edishtv.dao.WalletDao
}

object WalletDao {

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
      case e: Exception => println(e)
    }
  }

  def addAccount(userId : Int) : Boolean = {
    var isSuccess: Boolean = false
    try {
      establishConnection()
      val amount : Int = 0
      val query: String = s"INSERT INTO wallet (user_id, amount) VALUES ($userId, $amount);"
      statement.executeUpdate(query)
      isSuccess = true
    }
    catch {
      case e: Exception => println(e)
    }
    isSuccess
  }

  def addBalance(currentUser: User, amount: Int): Boolean = {
    var isSuccess : Boolean = false
    try {
      establishConnection()
      val userId : Int = currentUser.getUserId()
      var currentBalance : Int = 0
      var query : String = s"SELECT * FROM wallet WHERE user_id=$userId;"
      val resultSet : ResultSet = statement.executeQuery(query)
      if (resultSet.next())
        currentBalance = resultSet.getInt("amount")

      val wallet : Wallet = new Wallet(userId, currentBalance)
      wallet.addBalance(amount)
      val updatedBalance : Int = wallet.getBalance()
      query = s"UPDATE wallet SET amount=$updatedBalance WHERE user_id=$userId;"
      statement.executeUpdate(query)
      isSuccess = true
    }
    catch {
      case e : Exception => println(e)
    }
    isSuccess
  }

  def deductBalance(currentUser : User, amount : Int) : Boolean = {
    var isSuccess: Boolean = false
    try {
      establishConnection()
      val userId : Int = currentUser.getUserId()
      var currentBalance : Int = 0
      var query: String = s"SELECT * FROM wallet WHERE user_id=$userId;"
      val resultSet : ResultSet = statement.executeQuery(query)
      if (resultSet.next())
        currentBalance = resultSet.getInt("amount")
      val wallet : Wallet = new Wallet(userId, currentBalance)
      val isSubscriptionFeasible : Boolean = wallet.deductBalance(amount)
      if (isSubscriptionFeasible) {
        val updatedBalance : Int = wallet.getBalance()
        query = s"UPDATE wallet SET amount=$updatedBalance WHERE user_id=$userId;"
        statement.executeUpdate(query)
        isSuccess = true
      }
    }
    catch {
      case e: Exception => println(e)
    }
    isSuccess
  }

  def viewBalance(currentUser: User) : Int = {
    var balance : Int = -1
    try {
      establishConnection()
      val userId : Int = currentUser.getUserId()
      val query : String = s"SELECT * FROM wallet WHERE user_id=$userId;"
      val resultSet : ResultSet = statement.executeQuery(query)
      if (resultSet.next())
        balance = resultSet.getInt("amount")
    }
    catch {
      case e : Exception => println(e)
    }
    balance
  }

}