package com.vakulenkoalex.moneygateway

import android.app.Notification
import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        val packageName = sbn.packageName
        val extras = sbn.notification.extras
        val text = extras.getString(Notification.EXTRA_TEXT) ?: "Unknown"
        savePushToDatabase(
            context = this,
            sender = packageName,
            message = text
        )

    }

    private fun savePushToDatabase(
        context: Context,
        sender: String,
        message: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val smsDao = SmsRoomDatabase.getInstance(context).smsDao()
            smsDao.addSms(
                Sms(
                    sender = sender,
                    message = message,
                    type = SMSType.PUSH
                )
            )
        }
    }

}