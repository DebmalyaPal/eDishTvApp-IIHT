package com.edishtv

import org.slf4j.LoggerFactory
import java.sql.Timestamp

import com.edishtv.controller.Controller


/**
 * @author : Debmalya Pal
 */
object App {

  private val logger = LoggerFactory.getLogger(classOf[App])
  private var timestamp_now : Timestamp = _

  def main(args : Array[String]): Unit = {
    timestamp_now = new Timestamp(System.currentTimeMillis())
    logger.info(s"${timestamp_now.toString} : Application started...")

    Controller.menu()

    timestamp_now = new Timestamp(System.currentTimeMillis())
    logger.info(s"${timestamp_now.toString} : Application stopped...")
  }

}
