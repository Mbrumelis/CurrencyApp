package com.example.currencyapp


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.network.*
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.math.RoundingMode


class CurrencyListViewModel : ViewModel() {
    var client: CurrencyApiService = retrofit

    private val supervisorJob = SupervisorJob()

    var baseCurrencyObject = Currency("EUR", BigDecimal(1.0))
    val baseCurrency = MutableLiveData<Currency>(baseCurrencyObject)


    var baseCurrencyRate: BigDecimal = BigDecimal(1.0)

    private val currencyArray = ArrayList<Currency>()

    private val _currencyList = MutableLiveData<ArrayList<Currency>>()
    val currencyList: LiveData<ArrayList<Currency>>
        get() = _currencyList

    init {
        getCurrencyList()
        refreshCurrencyList()
    }


    private fun refreshCurrencyList() {
        supervisorJob.cancelChildren()
        CoroutineScope(Dispatchers.IO + supervisorJob).launch {
            while (this.isActive){
                delay(10000L)
                Log.d("Refr1", "Refreshed currency list")
                getCurrencyList()
            }
        }
    }

    private fun getCurrencyList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currencyList = client.getProperties(baseCurrencyObject.name)
                withContext(Dispatchers.Main) {
                    createCurrencyList(currencyList)
                }
            } catch(e: Throwable) {
                Log.d("Error123", e.message)
            }

        }
    }

    private fun createCurrencyList(currencyDTO: ExchangeRateResponseDTO) {
        if (currencyDTO != null) {
            currencyArray.clear()
            for ((key, value) in currencyDTO.rates) {
                val currency = Currency(key, value)
                currency.fullRate = baseCurrencyRate * value
                val roundedRate =
                    BigDecimal(currency.fullRate.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
                        .toString()
                currency.rateString = "1 ${baseCurrencyObject.name} = $roundedRate"
                currencyArray.add(currency)
            }
            _currencyList.value = currencyArray
        }
    }

    fun setNewRate(newRate: String){
        if (baseCurrencyRate != newRate.toBigDecimal()) {
            baseCurrencyRate = newRate.toBigDecimal()
            for (curr in currencyArray){
                curr.fullRate = curr.rate * baseCurrencyRate
            }
            _currencyList.value = currencyArray
        }
    }

    fun onListItemClick(currency: Currency) {
        baseCurrencyObject = Currency(currency.name, baseCurrencyObject.rate)
        baseCurrency.value = baseCurrencyObject
        getCurrencyList()
        refreshCurrencyList() // When a new currency is selected refreshes the timer to 0
    }


}
