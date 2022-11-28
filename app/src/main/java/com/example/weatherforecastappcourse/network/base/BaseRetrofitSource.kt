package com.example.weatherforecastappcourse.network.base

import android.os.Build
import android.security.keystore.BackendBusyException
import androidx.annotation.RequiresApi
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.text.ParseException

class BaseRetrofitSource(retrofitConfig: RetrofitConfig) {
    private val errorAdapter = retrofitConfig.moshi.adapter(ErrorResponseBody::class.java)
    @RequiresApi(Build.VERSION_CODES.S)
    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
       }catch (e: IOException){
            throw ConnectException(e.toString())
        }catch (e: HttpException){
            throw createBackendException(e)
        }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun createBackendException(e: HttpException): Exception{
        return try {
            val errorBody = errorAdapter.fromJson(e.response()!!.errorBody()!!.string())!!
            BackendBusyException(e.code().toLong(), errorBody.error)
        }catch (e: Exception){
            throw ParseException(e.toString(), 1)
        }
    }
    private data class ErrorResponseBody(
        val error: String
    )
}