package com.vakulenkoalex.moneygateway

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class SmsViewModel(application: Application) : ViewModel() {

    val allSms: LiveData<List<Sms>>
    private val repository: SmsRepository

    init {
        val smsDb = SmsRoomDatabase.getInstance(application)
        val smsDao = smsDb.smsDao()
        repository = SmsRepository(smsDao)
        allSms = repository.allSms
    }

    fun deleteAllSms() {
        repository.deleteAllSms()
    }
}