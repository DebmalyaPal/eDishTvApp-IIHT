package com.edishtv.service

import scala.io.StdIn.readLine
import org.slf4j.LoggerFactory
import java.sql.Timestamp
import scala.collection.mutable.ListBuffer

import com.edishtv.dao.ChannelDao
import com.edishtv.model.Channel


class ChannelService {
  import com.edishtv.service.ChannelService
}


object ChannelService {

  private val logger = LoggerFactory.getLogger(classOf[ChannelService])
  private var timestamp_now: Timestamp = _


  def addChannel() : Unit = {
    try {
      println("Enter details of TV Channel : ")
      print("Channel Name = ")
      val channelName: String = readLine()
      print("Channel Language = ")
      val language: String = readLine()
      print("Channel Description = ")
      val description: String = readLine()
      print("Channel Number = ")
      val channelNumber: String = readLine()
      print("Monthly Subscription Fee = ")
      val monthlySubscriptionFee: Int = readLine().toInt

      val newChannel: Channel = new Channel(
        channelName, language, description, channelNumber, monthlySubscriptionFee
      )

      val channel: Channel = ChannelDao.addChannel(newChannel)
      if (channel != null) {
        // Log = Channel (id) Added TIMESTAMP
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.info(s"${timestamp_now.toString} : TV Channel with Number - ${channel.getChannelNumber()} Added Successfully")

        println(s"TV Channel with Channel No. ${channel.getChannelNumber()} Added Successfully!")
      }
      else {
        // Log = Channel Add Operation Failed TIMESTAMP
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
      val isSuccess: Boolean = ChannelDao.updateChannel(updatedChannel)
      if (isSuccess) {
        // Log = Channel (id) Updated TIMESTAMP
        timestamp_now = new Timestamp(System.currentTimeMillis())
        logger.info(s"${timestamp_now.toString} : TV Channel with Number - ${updatedChannel.getChannelNumber()} Updated Successfully")

        println("Updated Channel Successfully!")
      }
      else {
        // Log = Channel (id) Failed to Update TIMESTAMP
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

  def viewChannelList(): Unit = {
    val channelList : ListBuffer[Channel] = ChannelDao.viewChannelList()
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
