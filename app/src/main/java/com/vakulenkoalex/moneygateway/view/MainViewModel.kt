package com.vakulenkoalex.moneygateway.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vakulenkoalex.moneygateway.room.GatewayRoomDatabase
import com.vakulenkoalex.moneygateway.room.Message
import com.vakulenkoalex.moneygateway.room.MessageRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.vakulenkoalex.moneygateway.AuthInterceptor
import com.vakulenkoalex.moneygateway.FireflyTransactions
import com.vakulenkoalex.moneygateway.FireflyTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class MainViewModel(application: Application) : ViewModel() {
    val allMessage: LiveData<List<Message>>
    var recordId by mutableIntStateOf(0)
    private val repository: MessageRepository
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZTBhYmQ5ZWMxMmMxZGU0YWFkZjhlZmJhYTZkM2Q3YmM0Y2Q1ZGY5YjNkNzlkZGRkOTAxYmVkYzk5ZTM3NWI1NDI5MWZjMTY4MWE3OTRlZGQiLCJpYXQiOjE3NDY1MTAxNTEuNzg5MjU5LCJuYmYiOjE3NDY1MTAxNTEuNzg5MjY2LCJleHAiOjE3NzgwNDYxNTAuNjkzMzkxLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.lsq6mzFGToo08lronvA7IpWSPXs4nG5Yb4emivj1b_upnZUeTwBq5U2omTt3eexC-hZq_Z9Y5UeQjHOfQZ3X9cx8ove8fXhhsUpg5sgUdudPuqeJPMS-u7ZwS_juTdAGHCxluOYJ7owL1ViG47evdzWc3XwztewOsNnEQlXvbkN-9hlGeimfvVyvZfBudsoHguK77VhJv2R8Qa-DKOlu4yiPflCphZpeKSbI9b9S-rLGMqVGBgsFBJ3JM98PSHZeOoB9AnXOeOCqSWTrBWDyJ3X-rCiaCK6KzX71PApPkHdbtjjFBer5gJ9pMlbr0-RpZZdb-VgaamgFm1yR0iNufKtDUwoSuDsJYvMCMhi9we9M-yIKoL-6otK1i1pwMB0ccSG8sflmZ_FpgPbDL6VpTLzdrq-327wDFlnPoctaAxGnsGJIOS6w-IGHi_krWf8TDeTDg2w1StsRmgXEOQ41FjI6OH_oIN0MsfYEv0c_3XegxAWrNsGCgy8p4h5s3c84TNo2zsG96XSEFu8y1u3mETu8Khd5gllGj4E9QSOAZbadsOK1O2trSygA_pzPoQCXiHi1P9DIg6GHD1LX5hPVS2KfeW8FGwHn2dyb4y9Uc70rkX2APcsuAJzf-36ROExLwaK7FzRiHVC7PRn_516eoqA3-Jst6vzIYQPUFF_iPbE"))
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
            val serverUrl = "http://192.168.7.140:3473/api/v1/transactions"
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