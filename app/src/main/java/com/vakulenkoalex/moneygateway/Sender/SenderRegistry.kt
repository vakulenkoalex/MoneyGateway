package com.vakulenkoalex.moneygateway.Sender

import com.vakulenkoalex.moneygateway.Sender.TBankSmsSender

object SenderRegistry {
    private val registry = mutableMapOf<String, Class<out Sender>>()

    init {
        register("6505551212", TBankSmsSender::class.java)
    }

    fun register(type: String, clazz: Class<out Sender>) {
        registry[type] = clazz
    }

    fun getSender(name: String): Sender? {
        return registry[name]?.newInstance()
    }
}