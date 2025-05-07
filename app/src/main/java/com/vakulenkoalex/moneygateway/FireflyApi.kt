package com.vakulenkoalex.moneygateway

import kotlinx.serialization.Serializable
import okhttp3.Interceptor
import okhttp3.Response

@Serializable
data class FireflyTransactions(
    val transactions: List<FireflyTransaction>
)

@Serializable
data class FireflyTransaction(
    val type: String,
    val date: String,
    val amount: Double,
    val description: String,
    val source_id: String
)

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}