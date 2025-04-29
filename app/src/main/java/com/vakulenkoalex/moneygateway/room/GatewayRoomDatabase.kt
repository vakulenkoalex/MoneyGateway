package com.vakulenkoalex.moneygateway.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [(Message::class)], version = 2)
@TypeConverters(MessageTypeConverter::class)
abstract class GatewayRoomDatabase: RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""ALTER TABLE message 
                    ADD COLUMN findSender INTEGER NOT NULL DEFAULT 0
                """)
            }
        }

        private var INSTANCE: GatewayRoomDatabase? = null
        fun getInstance(context: Context): GatewayRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GatewayRoomDatabase::class.java,
                        "gatewaydb"
                    ).addMigrations(MIGRATION_1_2)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}