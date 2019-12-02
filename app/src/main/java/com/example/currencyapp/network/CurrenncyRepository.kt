package com.example.currencyapp.network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyRepository {

    var client: CurrencyApiService = retrofit

    fun getCurrencyList(baseCurrency: Currency): LiveData<ExchangeRateResponseDTO> {
        val currencyLiveData = MutableLiveData<ExchangeRateResponseDTO>()
        CoroutineScope(Dispatchers.IO).launch {
            val currencyList = client.getProperties(baseCurrency.name)
            withContext(Dispatchers.Main) {
                currencyLiveData.value = currencyList
            }
        }
        return currencyLiveData
    }
}