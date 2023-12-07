package com.edishtv.controller

import java.sql.{Date, Timestamp}
import org.slf4j.LoggerFactory
import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine

import com.edishtv.model.{Channel, Subscription, User}
import com.edishtv.service.{ChannelService, SubscriptionService, TransactionService, WalletService}


class SubscriptionController {
  import com.edishtv.controller.SubscriptionController
}

object SubscriptionController {

  private val logger = LoggerFactory.getLogger(classOf[SubscriptionController])
  private var timestamp_now: Timestamp = _

  def subscribe(user : User) : Unit = {
    try {
      print("Enter Channel Id to Subscribe = ")
      val channelId: Int = readLine().toInt
      val channelToSubscribe : Channel = ChannelService.getChannelById(channelId)

      if (channelToSubscribe != null) {
        print("Number of months = ")
        val numberOfMonths : Int = readLine().toInt

        val monthlyCharge : Int = channelToSubscribe.getMonthlySubscriptionFee()
        val totalCharge : Int = monthlyCharge * numberOfMonths

        val isEnoughBalance : Boolean = WalletService.deductBalance(user, totalCharge)
        if (isEnoughBalance) {
          val isSuccess : Boolean = SubscriptionService.subscribe(user, channelToSubscribe, numberOfMonths)
          if (isSuccess) {
            TransactionService.addTransaction(user, channelToSubscribe, numberOfMonths)

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
      val channelToUnsubscribe : Channel = ChannelService.getChannelById(channelId)

      if (channelToUnsubscribe != null) {
        val isSuccess : Boolean = SubscriptionService.unsubscribe(user, channelId)
        if (isSuccess) {
          timestamp_now = new Timestamp(System.currentTimeMillis())
          logger.info(s"${timestamp_now.toString} : User (id - ${user.getUserId()}) " +
            s"Unsubscribed TV Channel with Channel No. $channelId")

          println(s"TV Channel with Channel No. ${channelToUnsubscribe.getChannelNumber()} " +
            "Unsubscribed Successfully!")
        }
        else
          println("Channel Not Subscribed.")
      }
      else
        println("Wrong Channel Id!")
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while Unsubscribing " +
          s"TV Channel by User(${user.getUserId()}) - $e")
      }
    }
  }

  def viewSubscription(user : User) : Unit = {
    val subscriptionList : ListBuffer[Subscription] = SubscriptionService.viewSubscription(user)
    if (subscriptionList.isEmpty)
      println("You are yet to make any Subscriptions!")
    else {
      var channel : Channel = null
      println("Subscription List :-")
      var count : Int = 0
      var totalSubscriptionCost : Int = 0
      for (subscription <- subscriptionList) {
        channel = ChannelService.getChannelById(subscription.getChannelId())
        val channelName: String = channel.getChannelName()
        val channelNumber: String = channel.getChannelNumber()
        val subscriptionCost: Int = subscription.getPrice()
        val startDate: Date = subscription.getStartDate()
        val expiryDate: Date = subscription.getExpiryDate()

        count += 1
        totalSubscriptionCost += subscriptionCost

        println(s"$count -- Channel Name = $channelName, Channel Number = $channelNumber, " +
          s"Subscription Amount = Rs. $subscriptionCost, Start Date = $startDate, " +
          s"Expiry Date = $expiryDate ")
      }
      println(s"Total Monthly Subscription Cost = Rs. $totalSubscriptionCost")
    }
  }

}
