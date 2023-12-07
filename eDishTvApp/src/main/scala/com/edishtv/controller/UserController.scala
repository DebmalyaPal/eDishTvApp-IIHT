package com.edishtv.controller


import org.slf4j.LoggerFactory
import java.sql.Timestamp
import scala.io.StdIn.readLine

import com.edishtv.model.{Admin, User}
import com.edishtv.service.{UserService, ChannelService, SubscriptionService, WalletService}


class UserController {

}

object UserController {

  private val logger = LoggerFactory.getLogger(classOf[UserController])
  private var timestamp_now: Timestamp = _

  def signup() : Unit = {
    println("Enter your details : ")
    print("Email : ")
    val email: String = readLine()
    print("First Name : ")
    val firstName: String = readLine()
    print("Last Name : ")
    val lastName: String = readLine()
    print("Password : ")
    val password: String = readLine()
    print("Confirm Password : ")
    val confirmPassword: String = readLine()

    if (password.equals(confirmPassword)) {
      val user: User = new User(email, firstName, lastName, password)
      val isSuccess : Boolean = UserService.signup(user)
      if (isSuccess) {
        // Log for User Sign Up with TIMESTAMP
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.info(s"${timestamp_now.toString} : User with email - ${user.getEmail()} signed up")

        println("User Registered Successfully!")
      }
      else {
        println("Account already exists!")
      }
    }
    else {
      println("Please enter same passwords.")
    }
  }

  def signin() : Unit = {
    print("User Email = ")
    val email: String = readLine()
    print("Password = ")
    val password: String = readLine()
    val userCheck: User = new User(email, password)

    val user : User = UserService.signin(userCheck)
    if (user != null) {
      // Log for User LogIn with TIMESTAMP
      timestamp_now = new Timestamp(System.currentTimeMillis())
      logger.info(s"${timestamp_now.toString} : User with Id - ${user.getUserId()} logged in.")

      UserController.menu(user)
    } else {
      println("Invalid Credentials -- Failed to SignIn!")
    }
  }

  def signout(user : User): Unit = {
    // Log for Admin Logout with TIMESTAMP
    timestamp_now = new Timestamp(System.currentTimeMillis())
    logger.info(s"${timestamp_now.toString} : User with Id = ${user.getUserId()} logged out")

    UserService.signout(user)
  }

  def menu(user : User): Unit = {
    var choice : String = "0"
    while (choice != "7") do {
      println()
      println("--- Welcome to eDishTV (logged in as User) ---")
      println("1 - Subscribe to a new TV Channel")
      println("2 - Unsubscribe to a TV Channel")
      println("3 - View Subscription Details")
      println("4 - View All TV Channels")
      println("5 - Add Balance")
      println("6 - View Balance")
      println("7 - Logout")
      print("Enter your choice = ")
      choice = readLine()

      choice match {
        case "1" => SubscriptionController.subscribe(user)
        case "2" => SubscriptionController.unsubscribe(user)
        case "3" => SubscriptionController.viewSubscription(user)
        case "4" => ChannelController.viewChannelList()
        case "5" => WalletController.addBalance(user)
        case "6" => WalletController.viewBalance(user)
        case "7" => UserController.signout(user)
        case _ => println("Please enter choice among given options")
      }
    }
    println()
  }

}