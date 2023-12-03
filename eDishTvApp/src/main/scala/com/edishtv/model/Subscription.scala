package com.edishtv.model

import java.sql.Date

class Subscription (var userId : Int,
                    var channelId : Int,
                    var charge : Int,
                    var startDate : Date,
                    var expiryDate : Date
                   ) {

  private var subscriptionId : Int = 0

  def this(subscriptionId : Int,
           userId : Int,
           channelId : Int,
           charge : Int,
           startDate : Date,
           expiryDate : Date
          ) = {
    this(userId, channelId, charge, startDate, expiryDate)
    this.subscriptionId = subscriptionId
  }

  def getSubscriptionId() : Int = {
    this.subscriptionId
  }

  def setUserId(userId : Int) : Unit = {
    this.userId = userId
  }

  def getUserId() : Int = {
    this.userId
  }

  def setChannelId(channelId: Int): Unit = {
    this.channelId = channelId
  }

  def getChannelId(): Int = {
    this.channelId
  }

  def setPrice(charge: Int): Unit = {
    this.charge = charge
  }

  def getPrice(): Int = {
    this.charge
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
