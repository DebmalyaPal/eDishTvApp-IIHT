package com.edishtv.service

import com.edishtv.model.Channel
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable.ListBuffer

class ChannelServiceTest extends AnyFunSuite {

  test ("Check total number of channels") {
    val count = ChannelService.viewChannelList().size
    var result : Boolean = count == 4
    assert(result)
  }

  test("Test get channel by Id") {
    val channel : Channel = ChannelService.getChannelById(1)
    var result: Boolean = false
    if (channel != null)
      result = channel.getChannelId()==1
    assert(result)
  }

}
