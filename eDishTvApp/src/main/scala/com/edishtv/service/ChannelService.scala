package com.edishtv.service

import scala.collection.mutable.ListBuffer

import com.edishtv.repository.ChannelDao
import com.edishtv.model.Channel


class ChannelService {
  import com.edishtv.service.ChannelService
}

object ChannelService {

  def addChannel(newChannel : Channel) : Channel = {
    val channel : Channel = ChannelDao.addChannel(newChannel)
    channel
  }

  def updateChannel(updateChannel : Channel) : Channel = {
    val channel : Channel = ChannelDao.updateChannel(updateChannel)
    channel
  }

  def viewChannelList() : ListBuffer[Channel] = {
    val channelList: ListBuffer[Channel] = ChannelDao.viewChannelList()
    channelList
  }

  def getChannelById(channelId : Int) : Channel = {
    val channel : Channel = ChannelDao.getChannelById(channelId)
    channel
  }

}
