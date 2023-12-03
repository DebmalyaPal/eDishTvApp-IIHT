package com.edishtv.service

import scala.io.StdIn.readLine
import com.edishtv.model.{Channel, Subscription, User}
import com.edishtv.dao.{ChannelDao, SubscriptionDao, TransactionDao, WalletDao}

import java.sql.Date
import scala.collection.mutable.ListBuffer

class SubscriptionService {
  import com.edishtv.service.SubscriptionService
}


object SubscriptionService {

  def viewSubscription(user : User) : Unit = {
    val subscriptionList: ListBuffer[Subscription] = SubscriptionDao.getAllSubscription(user)
    if (subscriptionList.isEmpty)
      println("You are yet to make any Subscriptions!")
    else {
      var channel: Channel = null
      println("Subscription List :-")
      var count: Int = 0
      for (subscription <- subscriptionList) {
        count += 1
        channel = ChannelDao.getChannelById(subscription.getChannelId())
        val channelName: String = channel.getChannelName()
        val channelNumber: String = channel.getChannelNumber()
        val subscriptionCost: Int = subscription.getPrice()
        val startDate: Date = subscription.getStartDate()
        val expiryDate: Date = subscription.getExpiryDate()
        println(s"$count -- Channel Name = $channelName, Channel Number = $channelNumber, " +
          s"Subscription Amount = Rs. $subscriptionCost, Start Date = $startDate, Expiry Date = $expiryDate ")
      }
    }
  }

  def subscribe(user : User) : Unit = {
    print("Enter Channel Id to Subscribe = ")
    val channelId : Int = readLine().toInt
    val channelToSubscribe : Channel = ChannelDao.getChannelById(channelId)

    if (channelToSubscribe != null) {
      val currentBalance : Int = WalletDao.viewBalance(user)
      val charge : Int = channelToSubscribe.getMonthlySubscriptionFee()
      val isEnoughBalance : Boolean = WalletDao.deductBalance(user, charge)
      if (isEnoughBalance) {
        val isSuccess : Boolean = SubscriptionDao.subscribe(user, channelToSubscribe)
        if (isSuccess) {
          TransactionDao.addTransaction(user, channelToSubscribe)
          // Log = User (id) subscribed to Channel (id) TIMESTAMP
          println(s"TV Channel with Channel No. ${channelToSubscribe.getChannelNumber()} Subscribed Successfully!")
        }
        else
          println("Failed to Subscribe to Channel.")
      }
      else
        println("Insufficient Balance! Please Add Balance and Re-try!")
    }
    else
      println("Wrong Channel Id!")
  }

  def unsubscribe(user : User) : Unit = {
    print("Enter Channel Id to Unsubscribe = ")
    val channelId : Int = readLine().toInt
    val channelToUnsubscribe : Channel = ChannelDao.getChannelById(channelId)

    if (channelToUnsubscribe != null) {
      val isSuccess: Boolean = SubscriptionDao.unsubscribe(user, channelId)
      if (isSuccess) {
        // Log = User (id) Unsubscribed from Channel (id) TIMESTAMP
        println(s"TV Channel with Channel No. ${channelToUnsubscribe.getChannelNumber()} Unsubscribed Successfully!")
      }
      else
        println("Channel Not Subscribed.")
    }
    else
      println("Wrong Channel Id!")
  }

}