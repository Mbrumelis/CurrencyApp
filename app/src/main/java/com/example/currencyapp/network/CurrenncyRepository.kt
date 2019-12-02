package com.example.currencyapp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyRepository {

    var client: CurrencyApiService = retrofit

    fun getCurrencyList(baseCurrency: Currency): LiveData<ExchangeRateResponseDTO> {

        val currencyLiveData = MutableLiveData<ExchangeRateResponseDTO>()

        client.getProperties(baseCurrency.name)
            .enqueue(object : Callback<ExchangeRateResponseDTO> {
                override fun onFailure(call: Call<ExchangeRateResponseDTO>, t: Throwable) {
                }
                override fun onResponse(call: Call<ExchangeRateResponseDTO>, response: Response<ExchangeRateResponseDTO>) {
                    currencyLiveData.value = response.body()
                }
            })
        return currencyLiveData
    }
}