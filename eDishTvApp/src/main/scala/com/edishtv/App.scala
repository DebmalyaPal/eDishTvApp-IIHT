package com.edishtv

import com.edishtv.controller.Controller

/**
 * @author ${user.name}
 */
object App {

  def main(args : Array[String]): Unit = {
    // Log = Application Started TIMESTAMP
    Controller.menu()
    // Log = Application Shutdown TIMESTAMP
    // (Convert all exceptions to logs)
  }

}
