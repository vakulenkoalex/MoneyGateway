package com.vakulenkoalex.moneygateway

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import com.vakulenkoalex.moneygateway.room.MessageType
import com.vakulenkoalex.moneygateway.view.SaveHelper

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
        val bundle = intent.extras ?: return
        val pdus: Array<*>? = bundle.get("pdus") as? Array<*> ?: return
        val format = bundle.getString("format")
        pdus?.forEach { pdu ->
            val bytes = pdu as? ByteArray
            val message = SmsMessage.createFromPdu(bytes, format)
            val sender = message.displayOriginatingAddress
            val text = message.messageBody
            SaveHelper.saveToDatabase(
                context = context,
                type = MessageType.SMS,
                sender = sender,
                text = text)
        }
    }
}
