package com.edishtv.service

import com.edishtv.model.User
import com.edishtv.repository.UserDao


class UserService {
  import com.edishtv.service.UserService
}

object UserService {

  def signup(user : User) : Boolean = {
    val isSuccess : Boolean = UserDao.signup(user)
    isSuccess
  }

  def signin(userCheck : User) : User = {
    val user : User = UserDao.signin(userCheck)
    user
  }

  def signout(user : User) : Unit = {
    println("Logged Out Successfully!")
  }

}