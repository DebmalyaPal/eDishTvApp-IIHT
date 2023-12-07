package com.edishtv.service

import com.edishtv.model.Admin
import com.edishtv.repository.AdminDao


class AdminService {
  import com.edishtv.service.AdminService
}

object AdminService {

  def signin(adminCheck : Admin) : Admin = {
    val admin : Admin = AdminDao.signin(adminCheck)
    admin
  }

  def signout(admin : Admin) : Unit = {
    println("Logged Out Successfully!")
  }

}