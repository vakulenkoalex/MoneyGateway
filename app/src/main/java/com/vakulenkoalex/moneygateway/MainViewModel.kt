package com.vakulenkoalex.moneygateway

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    val allSms: LiveData<List<Sms>>
    private val repository: SmsRepository
    private val _debugMode = mutableStateOf(false)
    val debugMode: State<Boolean> = _debugMode

    init {
        val smsDatabase = SmsRoomDatabase.getInstance(application)
        val smsDao = smsDatabase.smsDao()
        repository = SmsRepository(smsDao)
        allSms = repository.allSms
    }

    fun deleteAllSms() {
        repository.deleteAllSms()
    }

    fun toggleDebugMode(value: Boolean){
        _debugMode.value = value
    }

    fun saveToDatabase(
        type: SMSType,
        sender: String,
        message: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val find_sender = SenderRegistry.getSender(sender)
            if (_debugMode.value or (find_sender != null)) {
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
}