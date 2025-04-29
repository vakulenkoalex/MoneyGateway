package com.vakulenkoalex.moneygateway

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application) : ViewModel() {
    val allMessage: LiveData<List<Message>>
    private val repository: MessageRepository

    init {
        val gatewayDatabase = GatewayRoomDatabase.getInstance(application)
        repository = MessageRepository(gatewayDatabase.messageDao())
        allMessage = repository.allMessage
    }

    fun deleteAllMessage() {
        repository.deleteAllMessage()
    }
}