package com.edishtv.controller

import org.slf4j.LoggerFactory
import java.sql.Timestamp
import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine

import com.edishtv.model.Channel
import com.edishtv.service.{AdminService, ChannelService}
import com.edishtv.repository.ChannelDao


class ChannelController {
  import com.edishtv.controller.ChannelController
}

object ChannelController {

  private val logger = LoggerFactory.getLogger(classOf[ChannelController])
  private var timestamp_now: Timestamp = _

  def addChannel() : Unit = {
    try {
      println("Enter details of TV Channel : ")
      print("Channel Name = ")
      val channelName : String = readLine()
      print("Channel Language = ")
      val language : String = readLine()
      print("Channel Description = ")
      val description : String = readLine()
      print("Channel Number = ")
      val channelNumber : String = readLine()
      print("Monthly Subscription Fee = ")
      val monthlySubscriptionFee : Int = readLine().toInt

      val newChannel: Channel = new Channel(
        channelName, language, description, channelNumber, monthlySubscriptionFee
      )

      val channel: Channel = ChannelService.addChannel(newChannel)

      if (channel != null) {
        // Log for Channel (id) Added with TIMESTAMP
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.info(s"${timestamp_now.toString} : TV Channel with Number - ${channel.getChannelNumber()} Added Successfully")

        println(s"TV Channel with Channel No. ${channel.getChannelNumber()} Added Successfully!")
      }
      else {
        // Log for Channel Add Operation Failed with TIMESTAMP
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.info(s"${timestamp_now.toString} : Failed to Add TV Channel with Number - ${channel.getChannelNumber()}")

        println("Failed to Add New Channel.")
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while adding a new TV Channel - $e")
      }
    }
  }

  def updateChannel() : Unit = {
    try {
      print("Enter Id of Channel to Edit = ")
      val channelId: Int = readLine().toInt
      print("Updated Channel Name = ")
      val updatedChannelName: String = readLine()
      print("Updated Channel Language = ")
      val updatedChannelLanguage: String = readLine()
      print("Updated Channel Description = ")
      val updatedChannelDescription: String = readLine()
      print("Updated Channel Number = ")
      val updatedChannelNumber: String = readLine()
      print("Updated Channel Monthly Subscription Fee = Rs. ")
      val updatedMonthlySubscriptionFee: Int = readLine().toInt

      val updatedChannel: Channel = new Channel(
        channelId = channelId,
        channelName = updatedChannelName,
        language = updatedChannelLanguage,
        description = updatedChannelDescription,
        channelNumber = updatedChannelNumber,
        monthlySubscriptionFee = updatedMonthlySubscriptionFee
      )

      val channel : Channel = ChannelService.updateChannel(updatedChannel)
      if (channel != null) {
        // Log for Channel (id) Updated with TIMESTAMP
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.info(s"${timestamp_now.toString} : TV Channel with Number - ${updatedChannel.getChannelNumber()} Updated Successfully")

        println("Updated Channel Successfully!")
      }
      else {
        // Log for Channel (id) Failed to Update with TIMESTAMP
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.info(s"${timestamp_now.toString} : Failed to update TV Channel with Number - ${updatedChannel.getChannelNumber()}")

        println("No Channel with given Id exists OR Channel Number Already in Use!")
      }
    }
    catch {
      case e: Exception => {
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.error(s"${timestamp_now.toString} : Error while updating a new TV Channel - $e")
      }
    }
  }

  def viewChannelList() : Unit = {
    val channelList: ListBuffer[Channel] = ChannelService.viewChannelList()
    if (channelList.isEmpty)
      println("No Channels to Show!")
    else {
      println("Channel List :-")
      for (channel <- channelList) {
        println(channel)
      }
    }
  }

}