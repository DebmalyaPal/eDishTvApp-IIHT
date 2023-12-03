package com.edishtv.model

class User (var emailId : String,
            var firstName : String,
            var lastName : String,
            var password: String
           ) {

  private var userId : Int = 0

  def this (userId : Int,
            emailId : String,
            firstName : String,
            lastName : String,
            password : String
           ) {
    this(emailId, firstName, lastName, password)
    this.userId = userId
  }

  def this(emailId: String,
           password: String
          ) {
    this(emailId, "", "", password)
  }


  def getUserId() : Int = {
    this.userId
  }

  def setEmail(emailId : String): Unit = {
    this.emailId = emailId
  }

  def getEmail() : String = {
    this.emailId
  }

  def setFirstName(firstName : String): Unit = {
    this.firstName = firstName
  }

  def getFirstName(): String = {
    this.firstName
  }

  def setLastName(lastName: String): Unit = {
    this.lastName = lastName
  }

  def getLastName(): String = {
    this.lastName
  }

  def getFullName(): String = {
    val fullName : String = this.firstName + " " + this.lastName
    fullName
  }

  def setPassword(password : String): Unit = {
    this.password = password
  }

  def getPassword() : String = {
    this.password
  }


}