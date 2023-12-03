package com.edishtv.model


class Wallet (var userId : Int, var balance : Int = 0){

  def getUserId() : Int = {
    this.userId
  }

  def addBalance(amount : Int) : Unit = {
    this.balance += amount
  }

  def deductBalance(amount : Int) : Boolean = {
    var isFeasible : Boolean = false
    if (this.balance >= amount) {
      this.balance = this.balance - amount
      isFeasible = true
    }
    isFeasible
  }

  def getBalance() : Int = {
    this.balance
  }

}
