package com.edishtv.service

import scala.io.StdIn.readLine
import org.slf4j.LoggerFactory
import java.sql.{Date, Timestamp}
import scala.collection.mutable.ListBuffer

import com.edishtv.model.{Channel, Subscription, User}
import com.edishtv.dao.{ChannelDao, SubscriptionDao, TransactionDao, WalletDao}


class SubscriptionService {
  import com.edishtv.service.SubscriptionService
}


object SubscriptionService {

  private val logger = LoggerFactory.getLogger(classOf[SubscriptionService])
  private var timestamp_now: Timestamp = _


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
          s"Subscription Amount = Rs. $subscriptionCost, Start Date = $startDate, " +
          s"Expiry Date = $expiryDate ")
      }
    }
  }

  def subscribe(user : User) : Unit = {
    try {
      print("Enter Channel Id to Subscribe = ")
      val channelId: Int = readLine().toInt
      val channelToSubscribe: Channel = ChannelDao.getChannelById(channelId)

      if (channelToSubscribe != null) {
        val charge: Int = channelToSubscribe.getMonthlySubscriptionFee()
        val isEnoughBalance: Boolean = WalletDao.deductBalance(user, charge)
        if (isEnoughBalance) {
          val isSuccess: Boolean = SubscriptionDao.subscribe(user, channelToSubscribe)
          if (isSuccess) {
            TransactionDao.addTransaction(user, channelToSubscribe)
            // Log = User (id) subscribed to Channel (id) TIMESTAMP
            timestamp_now = new Timestamp(System.currentTimeMillis())
            logger.info(s"${timestamp_now.toString} : User (id - ${user.getUserId()}) " +
              s"subscribed to TV Channel with Channel No. ${channelToSubscribe.getChannelNumber()}")

            println(s"TV Channel with Channel No. ${channelToSubscribe.getChannelNumber()} " +
              s"Subscribed Successfully!")
          }
          else
            println("Failed to Subscribe to Channel.")
        }
        else {
          timestamp_now = new Timestamp(System.currentTimeMillis())
          logger.info(s"${timestamp_now.toString} : User(Id - ${user.getUserId()}) " +
            s"failed to subscribe TV Channel (Number - ${channelToSubscribe.getChannelNumber()} " +
            s"due to insufficient balance.")

          println("Insufficient Balance! Please Add Balance and Re-try!")
        }
      }
      else
        println("Wrong Channel Id!")
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Subscribing to " +
          s"TV Channel by User(${user.getUserId()}) - $e")
      }
    }
  }

  def unsubscribe(user : User) : Unit = {
    try {
      print("Enter Channel Id to Unsubscribe = ")
      val channelId: Int = readLine().toInt
      val channelToUnsubscribe: Channel = ChannelDao.getChannelById(channelId)

      if (channelToUnsubscribe != null) {
        val isSuccess: Boolean = SubscriptionDao.unsubscribe(user, channelId)
        if (isSuccess) {
          // Log = User (id) Unsubscribed from Channel (id) TIMESTAMP
          timestamp_now = new Timestamp(System.currentTimeMillis())
          logger.info(s"${timestamp_now.toString} : User (id - ${user.getUserId()}) " +
            s"Unsubscribed TV Channel with Channel No. $channelId")

          println(s"TV Channel with Channel No. ${channelToUnsubscribe.getChannelNumber()} " +
            s"Unsubscribed Successfully!")
        }
        else
          println("Channel Not Subscribed.")
      }
      else
        println("Wrong Channel Id!")
    }
    catch {
      case e : Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Unsubscribing " +
          s"TV Channel by User(${user.getUserId()}) - $e")
      }
    }
  }

}