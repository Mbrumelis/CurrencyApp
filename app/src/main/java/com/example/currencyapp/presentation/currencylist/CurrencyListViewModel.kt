package com.example.currencyapp.presentation.currencylist


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.domain.model.CurrencyDomainModel
import com.example.currencyapp.domain.usecases.GetCurrencyListUseCase
import kotlinx.coroutines.*
import java.math.BigDecimal


internal class CurrencyListViewModel(private val getCurrencyListUseCase: GetCurrencyListUseCase) : ViewModel() {


    private val supervisorJob = SupervisorJob()

    var baseCurrencyObject = CurrencyDomainModel("EUR", BigDecimal(1.0))
    val baseCurrency = MutableLiveData<CurrencyDomainModel>(baseCurrencyObject)

    private var currencyArray = ArrayList<CurrencyDomainModel>()

    private val _currencyList = MutableLiveData<ArrayList<CurrencyDomainModel>>()
    val currencyList: LiveData<ArrayList<CurrencyDomainModel>>
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
                val currencyListDomainModel = getCurrencyListUseCase.execute(baseCurrencyObject.name, baseCurrencyObject.rate)
                withContext(Dispatchers.Main) {
                    currencyArray = currencyListDomainModel!!.currencyList
                    _currencyList.value = currencyArray
                }
            } catch(e: Throwable) {
                Log.d("Error123", e.message)
            }

        }
    }


    fun setNewRate(newRate: String){
        if (baseCurrencyObject.rate != newRate.toBigDecimal()) {
            baseCurrencyObject.rate = newRate.toBigDecimal()
            for (curr in currencyArray){
                curr.fullRate = curr.rate * baseCurrencyObject.rate
            }
            _currencyList.value = currencyArray
        }
    }

    fun onListItemClick(currency: CurrencyDomainModel) {
        baseCurrencyObject =
            CurrencyDomainModel(currency.name, baseCurrencyObject.rate)
        baseCurrency.value = baseCurrencyObject
        getCurrencyList()
        refreshCurrencyList() // When a new currency is selected refreshes the timer to 0
    }


}
