package com.edishtv.service

import scala.io.StdIn.readLine

import com.edishtv.model.User
import com.edishtv.service.{ChannelService, SubscriptionService}
import com.edishtv.dao.UserDao

class UserService {
  import com.edishtv.service.UserService
}


object UserService {

  def signup(): Unit = {
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
      val isSuccess: Boolean = UserDao.signup(user)
      if (isSuccess) println("User Registered Successfully!")
      else println("Account already exists!")
    }
    else {
      println("Please enter same passwords.")
    }
  }

  def signin(): Unit = {
    print("User Email = ")
    val email: String = readLine()
    print("Password = ")
    val password: String = readLine()

    val userCheck: User = new User(email, password)
    val user: User = UserDao.signin(userCheck)

    if (user != null) {
      // Log - User LogIn TIMESTAMP
      UserService.menu(user)
    } else {
      println("Invalid Credentials -- Failed to SignIn!")
    }
  }

  def signout(user : User) : Unit = {
    // Log - User LogOut TIMESTAMP
    println("Logged Out Successfully!")
  }

  def menu(user : User): Unit = {
    var choice: String = "0"
    do {
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
        case "1" => SubscriptionService.subscribe(user)
        case "2" => SubscriptionService.unsubscribe(user)
        case "3" => SubscriptionService.viewSubscription(user)
        case "4" => ChannelService.viewChannelList()
        case "5" => WalletService.addBalance(user)
        case "6" => WalletService.viewBalance(user)
        case "7" => UserService.signout(user)
        case _ => println("Please enter choice among given options")
      }
    } while (choice != "7")
    println()
  }

}