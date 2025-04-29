package com.vakulenkoalex.moneygateway.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageRepository(private val messageDao: MessageDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val allMessage: LiveData<List<Message>> = messageDao.getAllMessage()

    fun addMessage(message: Message) {
        coroutineScope.launch(Dispatchers.IO) {
            messageDao.addMessage(message)
        }
    }

    fun deleteAllMessage() {
        coroutineScope.launch(Dispatchers.IO) {
            messageDao.deleteAllMessage()
        }
    }

}