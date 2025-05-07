package com.vakulenkoalex.moneygateway.view

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vakulenkoalex.moneygateway.AuthInterceptor
import com.vakulenkoalex.moneygateway.BuildConfig
import com.vakulenkoalex.moneygateway.FireflyTransaction
import com.vakulenkoalex.moneygateway.FireflyTransactions
import com.vakulenkoalex.moneygateway.room.GatewayRoomDatabase
import com.vakulenkoalex.moneygateway.room.Message
import com.vakulenkoalex.moneygateway.room.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainViewModel(application: Application) : ViewModel() {
    val allMessage: LiveData<List<Message>>
    var recordId by mutableIntStateOf(0)
    private val repository: MessageRepository
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(BuildConfig.FIREFLY_API_TOKEN))
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    init {
        val gatewayDatabase = GatewayRoomDatabase.Companion.getInstance(application)
        repository = MessageRepository(gatewayDatabase.messageDao())
        allMessage = repository.allMessage
    }

    fun deleteAllMessage() {
        repository.deleteAllMessage()
    }

    fun changeRecordId(value: String){
        recordId = value.toIntOrNull()?:recordId
    }

    fun createTransaction() {
        CoroutineScope(Dispatchers.IO).launch {
            val transactions  = listOf(
                FireflyTransaction(
                    type = "withdrawal",
                    date = "2025-05-01T12:46:47+01:00",
                    amount = 123.45,
                    description = "Vegetables",
                    source_id = "2"
                )
            )
            val a = FireflyTransactions(transactions)
            val jsonRequest = Json.encodeToString(a)
            val serverUrl = "http://${BuildConfig.FIREFLY_SERVER}/api/v1/transactions"
            val contentType = "application/json; charset=utf-8".toMediaType()
            val body: RequestBody = jsonRequest.toRequestBody(contentType)
            val request = Request.Builder().url(serverUrl).post(body).build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException(
                            "Запрос к серверу не был успешен:" +
                                    " ${response.code} ${response.message}"
                        )
                    }
                    println(response.body!!.string())
                }
            } catch (e: IOException) {
                println("Ошибка подключения: $e")
            }
        }
    }
}