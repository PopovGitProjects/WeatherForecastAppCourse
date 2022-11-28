package com.example.weatherforecastappcourse.network.interfaces

import com.example.weatherforecastappcourse.constants.Const
import com.example.weatherforecastappcourse.network.models.NetworkDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("{${Const.API_REST_VERSION}}/{${Const.API_TYPE_REQUEST}}?key={${Const.API_TOKEN}}&q={location}&days=3&aqi=no&alerts=no")
    suspend fun response(@Path ("location") location: String): NetworkDataModel
}