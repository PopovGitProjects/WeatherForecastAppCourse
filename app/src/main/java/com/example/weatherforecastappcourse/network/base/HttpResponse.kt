package com.example.weatherforecastappcourse.network.base

import android.util.Log
import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.network.interfaces.Api
import com.example.weatherforecastappcourse.network.models.NetworkDataModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HttpResponse: Api {
    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val moshi: Moshi = Moshi
        .Builder()
        .build()

    private val moshiConverterFactory: MoshiConverterFactory = MoshiConverterFactory
        .create(moshi)

    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(Const.API_BASE_URL)
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()

    private val api: Api = retrofit.create(Api::class.java)

    override suspend fun response(location: String): NetworkDataModel {
        Log.d("My", "Response: ${api.response(location).location.name}")
        return api.response(location = location)
    }
}