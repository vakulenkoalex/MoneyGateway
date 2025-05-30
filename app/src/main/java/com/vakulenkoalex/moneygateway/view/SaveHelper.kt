package com.vakulenkoalex.moneygateway.view

import android.content.Context
import com.vakulenkoalex.moneygateway.room.GatewayRoomDatabase
import com.vakulenkoalex.moneygateway.room.Message
import com.vakulenkoalex.moneygateway.room.MessageRepository
import com.vakulenkoalex.moneygateway.room.MessageType
import com.vakulenkoalex.moneygateway.sender.SenderRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SaveHelper {
    var debugMode: Boolean = false
        private set

    fun setDebugMode(enabled: Boolean) {
        debugMode = enabled
    }

    fun saveToDatabase(
        context: Context,
        type: MessageType,
        sender: String,
        text: String?
    ) {
        val gatewayDatabase = GatewayRoomDatabase.Companion.getInstance(context)
        val repository = MessageRepository(gatewayDatabase.messageDao())
        CoroutineScope(Dispatchers.IO).launch {
            val findSender = SenderRegistry.getSender(sender)
            val correctMessage = (findSender != null) and (text != null)
            if (debugMode or correctMessage) {
                repository.addMessage(
                    Message(
                        sender = sender,
                        text = text ?: "Unknown",
                        type = type,
                        findSender = (findSender != null)
                    )
                )
            }
        }
    }
}