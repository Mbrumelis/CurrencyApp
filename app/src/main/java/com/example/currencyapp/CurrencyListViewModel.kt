package com.example.currencyapp


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
    private val baseCurrency = "EUR"

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _currencyList = ArrayList<Currency>()
    val currencyList: ArrayList<Currency>
        get() = _currencyList

    init {
        getCurrencyList()
    }

    private fun getCurrencyList() {
        CurrencyApi.retrofitService.getProperties(baseCurrency).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failed: " + t.message
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null){

                    _currencyList.clear()
                    createCurrencyList(response.body()!!)
                    _response.value = _currencyList.toString()
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
            _currencyList.add(currency)
        }
    }



}
