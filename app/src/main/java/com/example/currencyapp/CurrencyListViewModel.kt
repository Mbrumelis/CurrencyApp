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


class CurrencyListViewModel : ViewModel() {


    val baseCurrency = MutableLiveData<String>()

    private val  currencyArray = ArrayList<Currency>()

    private val _currencyList = MutableLiveData<ArrayList<Currency>>()
    val currencyList: LiveData<ArrayList<Currency>>
        get() = _currencyList

    init {
        getCurrencyList()
    }

    fun getCurrencyList() {
        if (baseCurrency.value == null){
            baseCurrency.value = "EUR"
        }
        CurrencyApi.retrofitService.getProperties(baseCurrency.value!!).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null){

                    currencyArray.clear()
                    createCurrencyList(response.body()!!)
                }
            }
        })
    }

    private fun createCurrencyList (json: String){
        val jsonObject = JSONObject(json).getJSONObject("rates")

        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val currencyName = keys.next() as String

            val rate = jsonObject.getDouble(currencyName)
            val currency = Currency(currencyName, rate)
            currencyArray.add(currency)
            Log.d("Res1", currency.rate.toString())
        }
        _currencyList.value = currencyArray
        Log.d("Res1", baseCurrency.value)
    }



}
