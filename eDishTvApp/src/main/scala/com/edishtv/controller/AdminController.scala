package com.edishtv.controller

import org.slf4j.LoggerFactory
import java.sql.Timestamp
import scala.io.StdIn.readLine

import com.edishtv.model.Admin
import com.edishtv.service.{AdminService, ChannelService}


class AdminController {
  import com.edishtv.controller.AdminController
}

object AdminController {

  private val logger = LoggerFactory.getLogger(classOf[AdminController])
  private var timestamp_now : Timestamp = _

  def signin() : Unit = {
    print("Admin Email = ")
    val email: String = readLine()
    print("Password = ")
    val password: String = readLine()
    val adminCheck: Admin = new Admin(email, password)

    val admin: Admin = AdminService.signin(adminCheck)
    if (admin != null) {
      // Log for Admin Login with TIMESTAMP
      timestamp_now = new Timestamp(System.currentTimeMillis())
      logger.info(s"${timestamp_now.toString} : Admin with Id = ${admin.getAdminId()} logged in")

      AdminController.menu(admin)
    } else {
      println("Invalid Credentials -- Failed to SignIn!")
    }
  }

  def signout(admin : Admin) : Unit = {
    // Log for Admin Logout with TIMESTAMP
    timestamp_now = new Timestamp(System.currentTimeMillis())
    logger.info(s"${timestamp_now.toString} : Admin with Id = ${admin.getAdminId()} logged out")

    AdminService.signout(admin)
  }

  def menu(admin: Admin) : Unit = {
    var choice: String = "0"
    while (choice != "4") do {
      println()
      println("--- Welcome to eDishTV (logged in as Admin) ---")
      println("1 - Add a new TV Channel")
      println("2 - Update an existing TV Channel")
      println("3 - List all TV Channels")
      println("4 - Logout")
      print("Enter your choice = ")
      choice = readLine()

      choice match {
        case "1" => ChannelController.addChannel()
        case "2" => ChannelController.updateChannel()
        case "3" => ChannelController.viewChannelList()
        case "4" => AdminController.signout(admin)
        case _ => println("Please enter choice among given options")
      }
    }
  }

}