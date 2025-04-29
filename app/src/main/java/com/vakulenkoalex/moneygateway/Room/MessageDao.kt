package com.vakulenkoalex.moneygateway.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAllMessage(): LiveData<List<Message>>

    @Insert
    fun addMessage(message: Message)

    @Query("DELETE FROM message")
    fun deleteAllMessage()

}