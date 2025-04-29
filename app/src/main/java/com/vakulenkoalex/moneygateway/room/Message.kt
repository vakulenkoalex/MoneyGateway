package com.vakulenkoalex.moneygateway.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
class Message {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "text")
    var text: String
    @ColumnInfo(name = "sender")
    var sender: String
    @ColumnInfo(name = "timestamp")
    var timestamp: Long
    @ColumnInfo(name = "type")
    var type: MessageType

    constructor(text: String, sender: String, type: MessageType) {
        this.text = text
        this.sender = sender
        this.timestamp = System.currentTimeMillis()
        this.type = type
    }
}