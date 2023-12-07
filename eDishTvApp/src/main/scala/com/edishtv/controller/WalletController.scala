package com.edishtv.controller

import scala.io.StdIn.readLine
import java.sql.Timestamp
import org.slf4j.LoggerFactory

import com.edishtv.model.User
import com.edishtv.service.WalletService


class WalletController {
  import com.edishtv.controller.WalletController
}

object WalletController {

  private val logger = LoggerFactory.getLogger(classOf[WalletController])
  private var timestamp_now: Timestamp = _

  def addBalance(user : User) : Unit = {
    try {
      print("Enter Amount to Add (in Rs.) = ")
      val amount: Int = readLine().toInt
      WalletService.addBalance(user, amount)

      // Log for User (id) subscribed to Channel (id) with TIMESTAMP
      timestamp_now = new Timestamp(System.currentTimeMillis())
      logger.info(s"${timestamp_now.toString} : User (id - ${user.getUserId()}) added Rs. $amount to wallet")

      println(s"Rs. $amount Added Successfully!")
      WalletService.viewBalance(user)
    } catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : User (id - ${user.getUserId()}) failed to add money to wallet : $e")
      }
    }
  }

  def deductBalance(user : User, amount : Int) : Unit = {
    WalletService.deductBalance(user, amount)
  }

  def viewBalance(user : User) : Unit = {
    val balance : Int = WalletService.viewBalance(user)
    if (balance == -1) {
      println("Couldn't find Wallet for current user")
    }
    else {
      println(s"Current Balance = Rs. $balance")
    }
  }

}
