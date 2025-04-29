package com.vakulenkoalex.moneygateway.room

enum class MessageType(val displayName: String) {
    SMS("SMS"),
    PUSH("PUSH");

    companion object {
        fun fromDisplayName(displayName: String): MessageType {
            return MessageType.entries.first { it.displayName == displayName }
        }
    }
}