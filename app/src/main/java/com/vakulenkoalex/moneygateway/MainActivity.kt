package com.vakulenkoalex.moneygateway

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

class SmsViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SmsViewModel::class.java)) {
            return SmsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAndRequestPermissions()

        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: SmsViewModel = viewModel(
                    it,
                    "SmsViewModel",
                    SmsViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                Main(viewModel)
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val smsPermission = Manifest.permission.RECEIVE_SMS
        if (ContextCompat.checkSelfPermission(this, smsPermission) == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(smsPermission), PackageManager.PERMISSION_GRANTED)
        }

        if (! isNotificationAccessGranted(this)){
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }

    private fun isNotificationAccessGranted(context: Context): Boolean {
        val packages = NotificationManagerCompat.getEnabledListenerPackages(context)
        return packages.contains(context.packageName)
    }
}

@Composable
fun Main(vm: SmsViewModel) {
    val allSms by vm.allSms.observeAsState(listOf())
    Column {
        Button({ vm.deleteAllSms() }, Modifier.padding(8.dp)) {Text("Delete", fontSize = 22.sp)}
        SmsList(allSms)
    }
}
@Composable
fun SmsList(allSms:List<Sms>) {
    LazyColumn(Modifier.fillMaxWidth()) {
        item{ SmsTitleRow()}
        items(allSms) {u -> SmsRow(u) }
    }
}
@Composable
fun SmsRow(sms:Sms) {
    Row(Modifier .fillMaxWidth().padding(5.dp)) {
        Text(sms.id.toString(), Modifier.weight(0.1f), fontSize = 22.sp)
        Text(sms.message, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(sms.sender, Modifier.weight(0.2f), fontSize = 22.sp)
        Text(sms.type.displayName, Modifier.weight(0.2f), fontSize = 22.sp)
    }
}
@Composable
fun SmsTitleRow() {
    Row(Modifier.background(Color.LightGray).fillMaxWidth().padding(5.dp)) {
        Text("Id", color = Color.White,modifier = Modifier.weight(0.1f), fontSize = 22.sp)
        Text("Text", color = Color.White,modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Sender", color = Color.White, modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Text("Type", color = Color.White, modifier = Modifier.weight(0.2f), fontSize = 22.sp)
        Spacer(Modifier.weight(0.2f))
    }
}