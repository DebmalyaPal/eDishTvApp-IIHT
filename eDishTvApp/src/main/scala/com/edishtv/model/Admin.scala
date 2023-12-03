package com.edishtv.model


class Admin (var email : String,
             var password : String
            ) {

  private var adminId : Int = 0


  def this(id : Int,
           email : String,
           password: String) = {
    this(email, password)
    this.adminId = adminId
  }


  def getAdminId() : Int = {
    this.adminId
  }

  def setEmail(emailId : String): Unit = {
    this.email = emailId
  }

  def getEmail(): String = {
    this.email
  }

  def setPassword(password: String): Unit = {
    this.password = password
  }

  def getPassword(): String = {
    this.password
  }

}
