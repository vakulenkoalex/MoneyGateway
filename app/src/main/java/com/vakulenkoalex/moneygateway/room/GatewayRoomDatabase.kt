package com.vakulenkoalex.moneygateway.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

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