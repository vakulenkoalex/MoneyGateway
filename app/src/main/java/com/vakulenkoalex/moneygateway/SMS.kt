package com.vakulenkoalex.moneygateway

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms")
class Sms {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "message")
    var message: String
    @ColumnInfo(name = "sender")
    var sender: String
    @ColumnInfo(name = "timestamp")
    var timestamp: Long

    constructor(message: String, sender: String) {
        this.message = message
        this.sender = sender
        this.timestamp = System.currentTimeMillis()
    }
}