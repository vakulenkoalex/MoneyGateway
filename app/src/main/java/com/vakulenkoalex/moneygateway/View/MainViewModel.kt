package com.vakulenkoalex.moneygateway.View

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vakulenkoalex.moneygateway.Room.GatewayRoomDatabase
import com.vakulenkoalex.moneygateway.Room.Message
import com.vakulenkoalex.moneygateway.Room.MessageRepository

class MainViewModel(application: Application) : ViewModel() {
    val allMessage: LiveData<List<Message>>
    private val repository: MessageRepository

    init {
        val gatewayDatabase = GatewayRoomDatabase.Companion.getInstance(application)
        repository = MessageRepository(gatewayDatabase.messageDao())
        allMessage = repository.allMessage
    }

    fun deleteAllMessage() {
        repository.deleteAllMessage()
    }
}