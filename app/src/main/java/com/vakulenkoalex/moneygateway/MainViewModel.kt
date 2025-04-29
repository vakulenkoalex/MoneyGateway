package com.vakulenkoalex.moneygateway

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    val allMessage: LiveData<List<Message>>
    private val repository: MessageRepository
    private val _debugMode = mutableStateOf(false)
    val debugMode: State<Boolean> = _debugMode

    init {
        val gatewayDatabase = GatewayRoomDatabase.getInstance(application)
        repository = MessageRepository(gatewayDatabase.messageDao())
        allMessage = repository.allMessage
    }

    fun deleteAllMessage() {
        repository.deleteAllMessage()
    }

    fun toggleDebugMode(value: Boolean){
        _debugMode.value = value
    }

    fun saveToDatabase(
        type: MessageType,
        sender: String,
        message: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val findSender = SenderRegistry.getSender(sender)
            if (_debugMode.value or (findSender != null)) {
                repository.addMessage(
                    Message(
                        sender = sender,
                        text = message,
                        type = type
                    )
                )
            }
        }
    }
}