package com.edishtv.repository

import java.sql.{Connection, DriverManager, ResultSet, Statement, Timestamp}
import org.slf4j.LoggerFactory

import com.edishtv.model.User


class UserDao {
  import com.edishtv.repository.UserDao
}


object UserDao {

  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost/edishtv_db"
  private val username = "root"
  private val password = "1234"

  private var connection: Connection = _
  private var statement: Statement = _

  private val logger = LoggerFactory.getLogger(classOf[UserDao])
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

  def signup(newUser : User) : Boolean = {
    var isSuccess : Boolean = false
    try {
      var userAlreadyExist: Boolean = false
      establishConnection()
      var query: String = s"SELECT * FROM user WHERE email = '${newUser.getEmail()}'"
      var resultSet: ResultSet = statement.executeQuery(query)
      if (resultSet.next()) {
        userAlreadyExist = true
      }
      if (!userAlreadyExist) {
        val emailId = newUser.getEmail()
        val firstName = newUser.getFirstName()
        val lastName = newUser.getLastName()
        val password = newUser.getPassword()
        query =
          "INSERT INTO user (email, first_name, last_name, password) VALUES " +
            s"('$emailId', '$firstName', '$lastName', '$password');"
        statement.executeUpdate(query)

        query = s"SELECT id FROM user WHERE email = '$emailId';"
        resultSet = statement.executeQuery(query)
        if (resultSet.next()) {
          val userId : Int = resultSet.getInt("id")
          WalletDao.addAccount(userId)

          isSuccess = true
        }
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Registering a New User - $e")
      }
    }
    isSuccess
  }

  def signin(user : User) : User = {
    var currentUser : User = null
    try {
      val email = user.getEmail()
      val password = user.getPassword()

      establishConnection()
      val query: String = s"SELECT * FROM user WHERE email = '$email' AND password = '$password';"
      val resultSet: ResultSet = statement.executeQuery(query)
      if (resultSet.next()) {
        val userId: Int = resultSet.getInt("id")
        val firstName: String = resultSet.getString("first_name")
        val lastName: String = resultSet.getString("last_name")
        currentUser = new User(
          userId = userId, emailId = email, firstName = firstName, lastName = lastName, password = password
        )
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Logging In User - $e")
      }
    }
    currentUser
  }

}