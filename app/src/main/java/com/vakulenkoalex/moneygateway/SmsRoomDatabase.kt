package com.vakulenkoalex.moneygateway

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Sms::class)], version = 1)
abstract class SmsRoomDatabase: RoomDatabase() {

    abstract fun smsDao(): SmsDao

    companion object {
        private var INSTANCE: SmsRoomDatabase? = null
        fun getInstance(context: Context): SmsRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SmsRoomDatabase::class.java,
                        "usersdb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}