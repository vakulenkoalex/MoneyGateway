package com.vakulenkoalex.moneygateway

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            return
        }
        val bundle = intent.extras ?: return
        val pdus: Array<*>? = bundle.get("pdus") as? Array<*> ?: return
        val format = bundle.getString("format")
        pdus?.forEach { pdu ->
            val bytes = pdu as? ByteArray
            val message = SmsMessage.createFromPdu(bytes, format)
            val sender = message.displayOriginatingAddress?: "Unknown"
            val body = message.messageBody?: "Unknown"
            saveSmsToDatabase(
                context = context,
                sender = sender,
                message = body
            )
        }
    }

    private fun saveSmsToDatabase(
        context: Context,
        sender: String,
        message: String
    ) {
        val smsDb = SmsRoomDatabase.getInstance(context)
        val smsDao = smsDb.smsDao()
        val repository = SmsRepository(smsDao)
        CoroutineScope(Dispatchers.IO).launch {
            repository.addSms(
                Sms(
                    sender = sender,
                    message = message,
                    type = SMSType.SMS
                )
            )
        }
    }
}
