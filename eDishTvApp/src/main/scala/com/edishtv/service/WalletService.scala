package com.edishtv.service

import scala.io.StdIn.readLine

import com.edishtv.model.User
import com.edishtv.dao.WalletDao

class WalletService {
  import com.edishtv.service.WalletService
}


object WalletService {

  def addBalance(currentUser : User): Unit = {
    try {
      print("Enter Amount to Add (in Rs.) = ")
      val amount: Int = readLine().toInt
      WalletDao.addBalance(currentUser, amount)
      println(s"Rs. $amount Added Successfully!")
      viewBalance(currentUser)
    } catch {
      case e : Exception => e.printStackTrace()
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
