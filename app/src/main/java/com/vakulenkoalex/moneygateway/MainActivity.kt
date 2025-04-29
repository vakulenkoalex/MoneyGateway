package com.vakulenkoalex.moneygateway

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAndRequestPermissions()

        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: MainViewModel = viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                MessageView(viewModel)
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val smsPermission = Manifest.permission.RECEIVE_SMS
        if (ContextCompat.checkSelfPermission(this, smsPermission) == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(smsPermission), PackageManager.PERMISSION_GRANTED)
        }

        val packages = NotificationManagerCompat.getEnabledListenerPackages(this)
        if (! packages.contains(this.packageName)){
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }
}