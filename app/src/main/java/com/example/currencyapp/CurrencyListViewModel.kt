package com.example.currencyapp


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.network.Currency
import com.example.currencyapp.network.CurrencyApi
import com.example.currencyapp.network.ExchangeRateResponseDTO
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode


class CurrencyListViewModel : ViewModel() {

    var baseCurrencyObject = Currency("EUR", BigDecimal(1.0))
    val baseCurrency = MutableLiveData<Currency>(baseCurrencyObject)


    var baseCurrencyRate: BigDecimal = BigDecimal(1.0)

    private val currencyArray = ArrayList<Currency>()

    private val _currencyList = MutableLiveData<ArrayList<Currency>>()
    val currencyList: LiveData<ArrayList<Currency>>
        get() = _currencyList

    init {
        getCurrencyList()
    }

    fun getCurrencyList() {

        CurrencyApi.retrofitService.getProperties(baseCurrency.value!!.name)
            .enqueue(object : Callback<ExchangeRateResponseDTO> {
                override fun onFailure(call: Call<ExchangeRateResponseDTO>, t: Throwable) {
                }

                override fun onResponse(call: Call<ExchangeRateResponseDTO>, response: Response<ExchangeRateResponseDTO>) {
                    if (response.body() != null) {

                        currencyArray.clear()
                        createCurrencyList(response.body()!!)
                    }
                }
            })
    }

    private fun createCurrencyList(currencyDTO: ExchangeRateResponseDTO) {
        for((key, value) in currencyDTO.rates){
            val currency = Currency(key, value)
            currency.fullRate = baseCurrencyRate * value
            val roundedRate =
                BigDecimal(currency.fullRate.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
            currency.rateString = "1 ${baseCurrencyObject.name} = $roundedRate"
            currencyArray.add(currency)
        }
        _currencyList.value = currencyArray
    }

    fun setNewRate(newRate: String) {
            if (baseCurrencyRate != newRate.toBigDecimal()) {
                baseCurrencyRate = newRate.toBigDecimal()
                getCurrencyList()
            }
    }

    fun onListItemClick(currency: Currency) {
        baseCurrencyObject = Currency(currency.name, baseCurrencyObject.rate)
        baseCurrency.value = baseCurrencyObject
        getCurrencyList()

    }


}
