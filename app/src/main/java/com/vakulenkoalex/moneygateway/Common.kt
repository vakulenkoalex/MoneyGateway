package com.vakulenkoalex.moneygateway

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Common {
    fun saveToDatabase(
        type: SMSType,
        context: Context,
        sender: String,
        message: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val smsDb = SmsRoomDatabase.getInstance(context)
            val smsDao = smsDb.smsDao()
            val repository = SmsRepository(smsDao)
            repository.addSms(
                Sms(
                    sender = sender,
                    message = message,
                    type = type
                )
            )
        }
    }
}