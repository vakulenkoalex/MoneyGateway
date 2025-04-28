package com.vakulenkoalex.moneygateway

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class PushListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        val packageName = sbn.packageName
        val extras = sbn.notification.extras
        val title = extras.getString(Notification.EXTRA_TITLE) ?: "No title"
        val text = extras.getString(Notification.EXTRA_TEXT) ?: "No text"

        Log.d("NOTIFICATION", "App: $packageName | Title: $title | Text: $text")

    }

}