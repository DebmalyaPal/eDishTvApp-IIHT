package com.edishtv.model


class Channel (var channelName : String,
               var language : String,
               var description : String,
               var channelNumber : String,
               var monthlySubscriptionFee : Int
              ) {

  private var channelId : Int = 0

  def this (channelId : Int,
            channelName: String,
            language: String,
            description: String,
            channelNumber: String,
            monthlySubscriptionFee: Int
           ) = {
    this(channelName, language, description, channelNumber, monthlySubscriptionFee)
    this.channelId = channelId
  }


  def getChannelId() : Int = {
    this.channelId
  }

  def setChannelName(channelName : String) : Unit = {
    this.channelName = channelName
  }

  def getChannelName() : String = {
    this.channelName
  }

  def setDescription(description : String) = {
    this.description = description
  }

  def getDescription() : String = {
    this.description
  }

  def setLanguage(language : String) : Unit = {
    this.language = language
  }

  def getLanguage() : String = {
    this.language
  }

  def setChannelNumber(channelNumber : String) : Unit = {
    this.channelNumber = channelNumber
  }

  def getChannelNumber() : String = {
    this.channelNumber
  }

  def setMonthlySubscriptionFee(monthlySubscriptionFee : Int) = {
    this.monthlySubscriptionFee = monthlySubscriptionFee
  }

  def getMonthlySubscriptionFee() : Int = {
    this.monthlySubscriptionFee
  }


  override def toString: String = {
    val displayString : String =
      s"Channel Id = $channelId, Name = $channelName, Language = $language, Channel No. = $channelNumber, Monthly Cost = Rs. $monthlySubscriptionFee"
    displayString
  }


}
