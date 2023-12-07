package com.edishtv.service

import com.edishtv.model.{Channel, User}
import com.edishtv.repository.TransactionDao


class TransactionService {
}

object TransactionService {

  def addTransaction(user : User, channel : Channel, numberOfMonths : Int) : Unit = {
    TransactionDao.addTransaction(user, channel, numberOfMonths)
  }

}
