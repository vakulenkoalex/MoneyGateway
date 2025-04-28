package com.vakulenkoalex.moneygateway

enum class SMSType(val displayName: String) {
    SMS("SMS"),
    PUSH("PUSH");

    companion object {
        fun fromDisplayName(displayName: String): SMSType {
            return SMSType.entries.first { it.displayName == displayName }
        }
    }
}