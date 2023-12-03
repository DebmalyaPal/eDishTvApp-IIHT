package com.edishtv.service

import scala.io.StdIn.readLine
import org.slf4j.LoggerFactory
import java.sql.Timestamp

import com.edishtv.model.User
import com.edishtv.dao.WalletDao


class WalletService {
  import com.edishtv.service.WalletService
}


object WalletService {

  private val logger = LoggerFactory.getLogger(classOf[WalletService])
  private var timestamp_now: Timestamp = _

  def addBalance(currentUser : User): Unit = {
    try {
      print("Enter Amount to Add (in Rs.) = ")
      val amount: Int = readLine().toInt
      WalletDao.addBalance(currentUser, amount)

      // Log = User (id) subscribed to Channel (id) TIMESTAMP
      timestamp_now = new Timestamp(System.currentTimeMillis())
      logger.info(s"${timestamp_now.toString} : User (id - ${currentUser.getUserId()}) added Rs. $amount to wallet")

      println(s"Rs. $amount Added Successfully!")
      viewBalance(currentUser)
    } catch {
      case e : Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : User (id - ${currentUser.getUserId()}) failed to add money to wallet : $e")
      }
    }
  }

  def deductBalance(currentUser : User, amount : Int) : Unit = {
    println("Deducting Balance!!")
  }

  def viewBalance(currentUser : User) : Unit = {
    val balance : Int = WalletDao.viewBalance(currentUser)
    if (balance == -1) println("Couldn't find Wallet for current user")
    else println(s"Current Balance = Rs. $balance")
  }

}
