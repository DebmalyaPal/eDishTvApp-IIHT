package com.edishtv.service

import com.edishtv.model.User
import org.scalatest.funsuite.AnyFunSuite

class UserServiceTest extends AnyFunSuite {

  test("Valid User Login") {
    val userCheck : User = new User("debmalyapal.dp@gmail.com", "pass1234")
    val loggedInUser = UserService.signin(userCheck)
    var result: Boolean = false
    if (loggedInUser != null)
      result = loggedInUser.getEmail().equalsIgnoreCase(userCheck.getEmail())
    assert(result)
  }

  test("InValid User Login") {
    val userCheck: User = new User("debmalyapaldp@gmail.com", "passs1234")
    val loggedInUser = UserService.signin(userCheck)
    var result: Boolean = false
    if (loggedInUser != null)
      result = loggedInUser.getEmail().equalsIgnoreCase(userCheck.getEmail())
    assert(!result)
  }

}