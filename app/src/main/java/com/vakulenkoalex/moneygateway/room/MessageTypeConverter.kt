package com.vakulenkoalex.moneygateway.room

import androidx.room.TypeConverter

class MessageTypeConverter {
    @TypeConverter
    fun fromMessageType(value: MessageType): String {
        return value.displayName
    }

    @TypeConverter
    fun toMessageType(value: String): MessageType {
        return MessageType.fromDisplayName(value)
    }
}