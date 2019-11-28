package com.example.currencyapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.exchangeratesapi.io/"
private const val BASE_CUR = "latest?base="

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface CurrencyApiService {

    @GET(BASE_CUR)
    fun getProperties(@Query("currency") currency: String) :
            Call<String>
}

object CurrencyApi {
    val retrofitService : CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }
}