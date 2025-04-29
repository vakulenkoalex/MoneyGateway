package com.vakulenkoalex.moneygateway

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
        text: String
    ) {
        val gatewayDatabase = GatewayRoomDatabase.getInstance(context)
        val repository = MessageRepository(gatewayDatabase.messageDao())
        CoroutineScope(Dispatchers.IO).launch {
            val findSender = SenderRegistry.getSender(sender)
            if (debugMode or (findSender != null)) {
                repository.addMessage(
                    Message(
                        sender = sender,
                        text = text,
                        type = type,
                        findSender = (findSender != null)
                    )
                )
            }
        }
    }
}