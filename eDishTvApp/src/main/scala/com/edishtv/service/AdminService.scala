package com.edishtv.service

import scala.io.StdIn.readLine

import com.edishtv.model.Admin
import com.edishtv.dao.AdminDao
import com.edishtv.service.ChannelService


class AdminService {
  import com.edishtv.service.AdminService
}

object AdminService {

  def signin(): Unit = {
    print("Admin Email = ")
    val email : String = readLine()
    print("Password = ")
    val password : String = readLine()

    val adminCheck : Admin = new Admin(email, password)

    val admin : Admin = AdminDao.signin(adminCheck)

    if (admin != null) {
      // Log = Admin Login TIMESTAMP
      AdminService.menu()
    } else {
      println("Invalid Credentials -- Failed to SignIn!")
    }
  }

  def signout() : Unit = {
    // Log = Admin Logout TIMESTAMP
    println("Logged Out Successfully!")
  }

  def menu() : Unit = {
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
        case "4" => AdminService.signout()
        case _ => println("Please enter choice among given options")
      }
    } while (choice != "4")
  }

}
