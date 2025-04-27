package com.vakulenkoalex.moneygateway

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SmsDao {
    @Query("SELECT * FROM sms")
    fun getAllSms(): LiveData<List<Sms>>

    @Insert
    fun addSms(sms: Sms)

    @Query("DELETE FROM sms")
    fun deleteAllSms()

}