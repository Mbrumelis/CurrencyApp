package com.example.currencyapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
private const val BASE_URL = "https://api.exchangeratesapi.io/"

interface CurrencyApiService {
    @GET("latest")
    suspend fun getProperties(@Query("base") currency: String): ExchangeRateResponseDTO
}

val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(CurrencyApiService::class.java)
}
