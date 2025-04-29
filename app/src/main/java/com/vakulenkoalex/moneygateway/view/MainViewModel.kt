package com.vakulenkoalex.moneygateway.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vakulenkoalex.moneygateway.room.GatewayRoomDatabase
import com.vakulenkoalex.moneygateway.room.Message
import com.vakulenkoalex.moneygateway.room.MessageRepository

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