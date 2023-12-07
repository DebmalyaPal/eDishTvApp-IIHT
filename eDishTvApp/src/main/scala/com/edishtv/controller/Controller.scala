package com.edishtv.controller

import scala.io.StdIn.readLine


class Controller {
  import com.edishtv.controller
}

object Controller {

  def menu() : Unit = {
    var choice : String = ""
    while (choice != "5") do {
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
        case "1" => UserController.signup()
        case "2" => UserController.signin()
        case "3" => AdminController.signin()
        case "4" => ChannelController.viewChannelList()
        case "5" => println("Thank You!")
        case _ => println("Please enter choice among given options")
      }
      println()
    }
  }

}