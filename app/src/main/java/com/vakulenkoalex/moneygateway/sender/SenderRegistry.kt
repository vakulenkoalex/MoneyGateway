package com.vakulenkoalex.moneygateway.sender

object SenderRegistry {
    private val registry = mutableMapOf<String, Class<out Sender>>()

    init {
        register("com.idamob.tinkoff.android", TBankPushSender::class.java)
        register("900", SberbankSmsSender::class.java)
        register("T-Bank", TBankSmsSender::class.java)
    }

    fun register(type: String, clazz: Class<out Sender>) {
        registry[type] = clazz
    }

    fun getSender(name: String): Sender? {
        return registry[name]?.getDeclaredConstructor()?.newInstance()
    }
}