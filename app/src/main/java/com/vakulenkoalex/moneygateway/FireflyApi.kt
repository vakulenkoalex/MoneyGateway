package com.vakulenkoalex.moneygateway

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.time.OffsetDateTime
import retrofit2.Response as rResponse

interface FireflyService {
    @POST("transactions")
    suspend fun createTransactions(@Body data: FireflyTransactions): rResponse<FireflyResponse>
}

data class FireflyResponse(val result: String)

data class FireflyTransactions(
    val transactions: List<FireflyTransaction>
)

data class FireflyTransaction(
    val type: FireflyTransactionType,
    val date: OffsetDateTime,
    val amount: Double,
    val description: String,
    val source_id: String
)

enum class FireflyTransactionType {
    WITHDRAWAL,
    DEPOSIT,
    TRANSFER
}

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}