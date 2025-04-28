package com.vakulenkoalex.moneygateway

import androidx.room.TypeConverter

class SMSTypeConverter {
    @TypeConverter
    fun fromSMSType(value: SMSType): String {
        return value.displayName
    }

    @TypeConverter
    fun toSMSType(value: String): SMSType {
        return SMSType.fromDisplayName(value)
    }
}