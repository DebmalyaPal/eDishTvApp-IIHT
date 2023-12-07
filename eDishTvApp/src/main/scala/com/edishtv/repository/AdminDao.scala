package com.edishtv.repository

import org.slf4j.LoggerFactory
import java.sql.{Connection, DriverManager, ResultSet, Statement, Timestamp}

import com.edishtv.model.Admin


class AdminDao {
  import com.edishtv.repository.AdminDao
}

object AdminDao {
  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost/edishtv_db"
  private val username = "root"
  private val password = "1234"

  private var connection : Connection = _
  private var statement : Statement = _

  private val logger = LoggerFactory.getLogger(classOf[AdminDao])
  private var timestamp_now : Timestamp = _

  private def establishConnection() : Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      statement = connection.createStatement()
    }
    catch {
      case e : Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Failed to establish connection - $e")
      }
    }
  }

  def signin(adminCheck : Admin) : Admin = {
    var admin: Admin = null
    try {
      val emailId: String = adminCheck.getEmail()
      val password: String = adminCheck.getPassword()

      establishConnection()
      val query: String =
        s"SELECT * FROM ADMIN WHERE email = '$emailId' AND password = '$password'"
      val resultSet: ResultSet = statement.executeQuery(query)
      if (resultSet.next()) {
        val adminId = resultSet.getInt("id")
        val email = resultSet.getString("email")
        val password = resultSet.getString("password")

        admin = new Admin(adminId, email, password)
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error Signing In Admin - $e")
      }
    }
    admin
  }

}