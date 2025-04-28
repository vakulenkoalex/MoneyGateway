package com.vakulenkoalex.moneygateway

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms")
class Sms {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "message")
    var message: String
    @ColumnInfo(name = "sender")
    var sender: String
    @ColumnInfo(name = "timestamp")
    var timestamp: Long
    @ColumnInfo(name = "type")
    var type: SMSType

    constructor(message: String, sender: String, type: SMSType) {
        this.message = message
        this.sender = sender
        this.timestamp = System.currentTimeMillis()
        this.type = type
    }
}