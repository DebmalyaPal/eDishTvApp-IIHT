package com.edishtv.service

import scala.io.StdIn.readLine
import scala.collection.mutable.ListBuffer

import com.edishtv.dao.ChannelDao
import com.edishtv.model.Channel


class ChannelService {
  import com.edishtv.service.ChannelService
}


object ChannelService {

  def addChannel() : Unit = {
    println("Enter details of TV Channel : ")
    print("Channel Name = ")
    val channelName : String = readLine()
    print("Channel Language = ")
    val language: String = readLine()
    print("Channel Description = ")
    val description: String = readLine()
    print("Channel Number = ")
    val channelNumber: String = readLine()
    print("Monthly Subscription Fee = ")
    val monthlySubscriptionFee: Int = readLine().toInt

    val newChannel : Channel = new Channel(
      channelName, language, description, channelNumber, monthlySubscriptionFee
    )

    val channel: Channel = ChannelDao.addChannel(newChannel)
    if (channel != null)  {
      // Log = Channel (id) Added TIMESTAMP
      println(s"TV Channel with Channel No. ${channel.getChannelNumber()} Added Successfully!")
    }
    else {
      // Log = Channel Add Operation Failed TIMESTAMP
      println("Failed to Add New Channel.")
    }
  }

  def updateChannel() : Unit = {
    print("Enter Id of Channel to Edit = ")
    val channelId : Int = readLine().toInt
    print("Updated Channel Name = ")
    val updatedChannelName : String = readLine()
    print("Updated Channel Language = ")
    val updatedChannelLanguage: String = readLine()
    print("Updated Channel Description = ")
    val updatedChannelDescription: String = readLine()
    print("Updated Channel Number = ")
    val updatedChannelNumber: String = readLine()
    print("Updated Channel Monthly Subscription Fee = Rs. ")
    val updatedMonthlySubscriptionFee: Int = readLine().toInt

    val updatedChannel : Channel = new Channel(
      channelId = channelId,
      channelName = updatedChannelName,
      language = updatedChannelLanguage,
      description = updatedChannelDescription,
      channelNumber = updatedChannelNumber,
      monthlySubscriptionFee = updatedMonthlySubscriptionFee
    )

    val isSuccess : Boolean = ChannelDao.updateChannel(updatedChannel)

    if (isSuccess) {
      // Log = Channel (id) Updated TIMESTAMP
      println("Updated Channel Successfully!")
    }
    else {
      // Log = Channel (id) Failed to Update TIMESTAMP
      println("No Channel with given Id exists OR Channel Number Already in Use!")
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
