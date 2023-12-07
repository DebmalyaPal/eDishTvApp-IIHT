package com.edishtv.service

import scala.collection.mutable.ListBuffer

import com.edishtv.model.{User, Channel, Subscription}
import com.edishtv.repository.SubscriptionDao


class SubscriptionService {
  import com.edishtv.service.SubscriptionService
}

object SubscriptionService {

  def viewSubscription(user : User) : ListBuffer[Subscription] = {
    val subscriptionList: ListBuffer[Subscription] = SubscriptionDao.getAllSubscription(user)
    subscriptionList
  }

  def subscribe(user : User, channel : Channel, numberOfMonths : Int) : Boolean = {
    var isSuccess : Boolean = SubscriptionDao.subscribe(user, channel, numberOfMonths)
    isSuccess
  }

  def unsubscribe(user : User, channelId : Int) : Boolean = {
    val isSuccess = SubscriptionDao.unsubscribe(user, channelId)
    isSuccess
  }

}