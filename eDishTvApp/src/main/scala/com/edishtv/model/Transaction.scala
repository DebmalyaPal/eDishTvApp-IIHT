package com.edishtv.model

import java.sql.Date

class Transaction (var userId : Int,
                   var channelId : Int,
                   var price : Int,
                   var transactionDate : Date,
                   var startDate : Date,
                   var expiryDate : Date
                  ) {

  private var transactionId : Int = 0


  def getTransactionId() : Int = {
    this.transactionId
  }

  def setUserId(userId : Int) = {
    this.userId = userId
  }

  def getUserId() : Int = {
    this.userId
  }

  def setChannelId(channelId: Int): Unit = {
    this.channelId = channelId
  }

  def getChannelId() : Int = {
    this.channelId
  }

  def setPrice(price : Int) : Unit = {
    this.price = price
  }

  def getPrice(): Int = {
    this.price
  }

  def setTransactionDate(transactionDate : Date): Unit = {
    this.transactionDate = transactionDate
  }

  def getTransactionDate(): Date = {
    this.transactionDate
  }

  def setStartDate(startDate: Date): Unit = {
    this.startDate = startDate
  }

  def getStartDate(): Date = {
    this.startDate
  }

  def setExpiryDate(expiryDate: Date): Unit = {
    this.expiryDate = expiryDate
  }

  def getExpiryDate(): Date = {
    this.expiryDate
  }


}
