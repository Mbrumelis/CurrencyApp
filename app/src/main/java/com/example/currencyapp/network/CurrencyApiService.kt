package com.example.currencyapp.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.exchangeratesapi.io/"
private const val BASE_CUR = "latest?base=EUR"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CurrencyApiService {

    @GET(BASE_CUR)
    fun getProperties() :
            Call<String>
}

object CurrencryApi {
    val retrofitService : CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }
}