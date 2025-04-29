package com.vakulenkoalex.moneygateway.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vakulenkoalex.moneygateway.Room.Message
import com.vakulenkoalex.moneygateway.Room.MessageDao
import com.vakulenkoalex.moneygateway.Room.MessageTypeConverter

@Database(entities = [(Message::class)], version = 1)
@TypeConverters(MessageTypeConverter::class)
abstract class GatewayRoomDatabase: RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        private var INSTANCE: GatewayRoomDatabase? = null
        fun getInstance(context: Context): GatewayRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GatewayRoomDatabase::class.java,
                        "gatewaydb"
                    ).fallbackToDestructiveMigration(false).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}