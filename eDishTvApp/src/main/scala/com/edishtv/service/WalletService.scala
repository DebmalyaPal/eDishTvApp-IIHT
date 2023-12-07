package com.edishtv.service

import com.edishtv.model.User
import com.edishtv.repository.WalletDao


class WalletService {
  import com.edishtv.service.WalletService
}


object WalletService {

  def addBalance(currentUser : User, amount : Int) : Unit = {
    WalletDao.addBalance(currentUser, amount)
  }

  def deductBalance(currentUser : User, amount : Int) : Boolean = {
    val isSuccess = WalletDao.deductBalance(currentUser, amount)
    isSuccess
  }

  def viewBalance(currentUser : User) : Int = {
    val balance : Int = WalletDao.viewBalance(currentUser)
    balance
  }

}
