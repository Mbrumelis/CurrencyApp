package com.example.currencyapp.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.exchangeratesapi.io/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface CurrencyApiService {

    @GET("latest")
    fun getProperties(@Query("base") currency: String) :
            Call<ExchangeRateResponseDTO>
}

object CurrencyApi {
    val retrofitService : CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }
}