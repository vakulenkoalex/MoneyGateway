package com.vakulenkoalex.moneygateway

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsRepository(private val smsDao: SmsDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val allSms: LiveData<List<Sms>> = smsDao.getAllSms()

    fun addSms(sms: Sms) {
        coroutineScope.launch(Dispatchers.IO) {
            smsDao.addSms(sms)
        }
    }

    fun deleteAllSms() {
        coroutineScope.launch(Dispatchers.IO) {
            smsDao.deleteAllSms()
        }
    }

}