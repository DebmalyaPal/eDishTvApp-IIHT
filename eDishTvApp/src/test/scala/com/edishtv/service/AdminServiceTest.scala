package com.edishtv.service

import com.edishtv.model.Admin
import com.edishtv.repository.AdminDao
import org.scalatest.funsuite.AnyFunSuite


class AdminServiceTest extends AnyFunSuite {

  test ("Valid Admin Login") {
    val adminCheck : Admin = new Admin("admin@iiht.com", "iiht@1234")
    val loggedInAdmin = AdminService.signin(adminCheck)
    var result: Boolean = false
    if (loggedInAdmin != null)
      result = loggedInAdmin.getEmail().equalsIgnoreCase(adminCheck.getEmail())
    assert(result)
  }

  test("Invalid Admin Login") {
    val adminCheck: Admin = new Admin("admin@test.com", "test@1234")
    val loggedInAdmin = AdminService.signin(adminCheck)
    var result: Boolean = false
    if (loggedInAdmin != null)
      result = loggedInAdmin.getEmail().equalsIgnoreCase(adminCheck.getEmail())
    assert(!result)
  }

}