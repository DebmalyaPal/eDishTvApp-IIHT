package com.edishtv.controller

import scala.io.StdIn.readLine

import com.edishtv.service.AdminService
import com.edishtv.service.UserService
import com.edishtv.service.ChannelService

class Controller {

  import com.edishtv.controller._;

}

object Controller {

  def menu() : Unit = {
    println("Controller...")

    var choice : String = ""

    do {
      println("Please select your option :")
      println("1 - Register as User")
      println("2 - User Login")
      println("3 - Admin Login")
      println("4 - View all existing TV Channels")
      println("5 - Exit")
      print("Enter you option = ")

      choice = readLine()

      println()

      choice match {
        case "1" => UserService.signup()
        case "2" => UserService.signin()
        case "3" => AdminService.signin()
        case "4" => ChannelService.viewChannelList()
        case "5" => println("Thank You!")
        case _ => println("Please enter choice among given options")
      }

      println()

    } while (choice != "5")

  }

}