package com.edishtv.dao

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import com.edishtv.model.Admin


class AdminDao {
  import com.edishtv.dao.AdminDao
}


object AdminDao {
  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost/edishtv_db"
  private val username = "root"
  private val password = "1234"

  private var connection : Connection = _
  private var statement : Statement = _

  private def establishConnection() : Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      statement = connection.createStatement()
    }
    catch {
      case e : Exception => println(e)
    }
  }

  def signin(adminCheck : Admin) : Admin = {
    var admin : Admin = null

    val emailId : String = adminCheck.getEmail()
    val password : String = adminCheck.getPassword()

    establishConnection()
    val query : String =
      s"SELECT * FROM ADMIN WHERE email = '$emailId' AND password = '$password'"
    val resultSet : ResultSet = statement.executeQuery(query)
    if (resultSet.next()) {
      val adminId = resultSet.getInt("id")
      val email = resultSet.getString("email")
      val password = resultSet.getString("password")

      admin = new Admin(adminId, email, password)
    }
    admin
  }

}