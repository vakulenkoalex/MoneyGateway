package com.vakulenkoalex.moneygateway

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class PushListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val extras = sbn.notification.extras
        val text = extras.getString(Notification.EXTRA_TEXT) ?: "Unknown"
        MessageHelper.saveToDatabase(
            context = this,
            type = MessageType.PUSH,
            sender = packageName,
            text = text
        )
    }
}