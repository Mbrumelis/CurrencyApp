package com.example.currencyapp.data.retrofit

import com.example.currencyapp.data.model.CurrencyListDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


    private const val BASE_URL = "https://api.exchangeratesapi.io/"

    internal interface CurrencyApiService {
        @GET("latest")
        suspend fun getProperties(@Query("base") currency: String): CurrencyListDTO
    }

    fun getMockRetrofit():Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    internal fun getMockApiService(retrofit: Retrofit): CurrencyApiService = retrofit.create(CurrencyApiService::class.java)




