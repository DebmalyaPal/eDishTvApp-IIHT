package com.edishtv.service

import scala.io.StdIn.readLine
import java.sql.Timestamp
import org.slf4j.LoggerFactory

import com.edishtv.model.Admin
import com.edishtv.dao.AdminDao


class AdminService {
  import com.edishtv.service.AdminService
}

object AdminService {

  private val logger = LoggerFactory.getLogger(classOf[AdminService])
  private var timestamp_now: Timestamp = _

  def signin(): Unit = {
    print("Admin Email = ")
    val email : String = readLine()
    print("Password = ")
    val password : String = readLine()
    val adminCheck : Admin = new Admin(email, password)

    val admin : Admin = AdminDao.signin(adminCheck)
    if (admin != null) {
      // Log = Admin Login TIMESTAMP
      timestamp_now = new Timestamp(System.currentTimeMillis())
      logger.info(s"${timestamp_now.toString} : Admin with Id = ${admin.getAdminId()} logged in")

      AdminService.menu(admin)
    } else {
      println("Invalid Credentials -- Failed to SignIn!")
    }
  }

  def signout(admin : Admin) : Unit = {
    // Log = Admin Logout TIMESTAMP
    timestamp_now = new Timestamp(System.currentTimeMillis())
    logger.info(s"${timestamp_now.toString} : Admin with Id = ${admin.getAdminId()} logged out")

    println("Logged Out Successfully!")
  }

  def menu(admin : Admin) : Unit = {
    var choice: String = "0"
    do {
      println()
      println("--- Welcome to eDishTV (logged in as Admin) ---")
      println("1 - Add a new TV Channel")
      println("2 - Update an existing TV Channel")
      println("3 - List all TV Channels")
      println("4 - Logout")
      print("Enter your choice = ")
      choice = readLine()

      choice match {
        case "1" => ChannelService.addChannel()
        case "2" => ChannelService.updateChannel()
        case "3" => ChannelService.viewChannelList()
        case "4" => AdminService.signout(admin)
        case _ => println("Please enter choice among given options")
      }
    } while (choice != "4")
  }

}