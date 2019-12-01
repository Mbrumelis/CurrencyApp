package com.example.currencyapp


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.network.Currency
import com.example.currencyapp.network.CurrencyApi
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode


class CurrencyListViewModel : ViewModel() {

    var baseCurrencyObject = Currency("EUR", 1.0)
    val baseCurrency = MutableLiveData<Currency>(baseCurrencyObject)


    var baseCurrencyRate: Double = 1.0

    private val currencyArray = ArrayList<Currency>()

    private val _currencyList = MutableLiveData<ArrayList<Currency>>()
    val currencyList: LiveData<ArrayList<Currency>>
        get() = _currencyList

    init {
        getCurrencyList()
    }

    fun getCurrencyList() {

        CurrencyApi.retrofitService.getProperties(baseCurrency.value!!.name)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body() != null) {

                        currencyArray.clear()
                        createCurrencyList(response.body()!!)
                    }
                }
            })
    }

    private fun createCurrencyList(json: String) {
        val jsonObject = JSONObject(json).getJSONObject("rates")

        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val currencyName = keys.next() as String

            val rate = jsonObject.getDouble(currencyName)
            val currency = Currency(currencyName, rate)
            currency.fullRate = baseCurrencyRate * rate
            val roundedRate =
                BigDecimal(currency.fullRate).setScale(2, RoundingMode.HALF_EVEN).toString()

            currency.rateString = "1 ${baseCurrencyObject.name} = $roundedRate"
            currencyArray.add(currency)
        }
        _currencyList.value = currencyArray
    }

    fun setNewRate(newRate: String) {
            if (baseCurrencyRate != newRate.toDouble()) {
                baseCurrencyRate = newRate.toDouble()
                getCurrencyList()
            }
    }

    fun onListItemClick(currency: Currency) {
        baseCurrencyObject = Currency(currency.name, baseCurrencyObject.rate)
        baseCurrency.value = baseCurrencyObject
        getCurrencyList()

    }


}
