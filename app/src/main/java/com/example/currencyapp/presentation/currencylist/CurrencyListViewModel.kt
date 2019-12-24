package com.example.currencyapp.presentation.currencylist


import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap
import com.example.currencyapp.domain.model.CurrencyDomainModel
import com.example.currencyapp.domain.model.toViewableList
import com.example.currencyapp.domain.usecases.GetCurrencyListUseCase
import kotlinx.coroutines.*
import java.math.BigDecimal


internal class CurrencyListViewModel(private val getCurrencyListUseCase: GetCurrencyListUseCase) :
    ViewModel() {


    var baseCurrencyObject = CurrencyDomainModel("EUR", BigDecimal(1.0))
    val baseCurrency = MutableLiveData<CurrencyDomainModel>(baseCurrencyObject)

    private var currencyArray = ArrayList<CurrencyDomainModel>()



    val viewCurrencyList: MutableLiveData<ArrayList<CurrencyDomainModel>>
        get() = currencyList as MutableLiveData<ArrayList<CurrencyDomainModel>>


    private val currencyList: LiveData<ArrayList<CurrencyDomainModel>> = switchMap(baseCurrency) {
        liveData {
            while (true) {
                try {
                    emit(getNewRate())
                } catch (e: Throwable) {
                    Log.d("Error123", e.message)
                }
                delay(10000L)
            }
        }
    }

    private suspend fun getNewRate(): ArrayList<CurrencyDomainModel> {
            val currencyListDomainList =
                getCurrencyListUseCase.execute(
                    baseCurrencyObject.name,
                    baseCurrencyObject.rate
                )?.toViewableList()
            Log.d("DEBUG123", "New List" + baseCurrencyObject.name)
            currencyArray = currencyListDomainList!!
            return currencyListDomainList
    }


    fun setNewRate(newRate: String) {
        if (baseCurrencyObject.rate != newRate.toBigDecimal()) {
            baseCurrencyObject.rate = newRate.toBigDecimal()
            for (curr in currencyArray) {
                curr.fullRate = curr.rate * baseCurrencyObject.rate
            }
            viewCurrencyList.value = currencyArray
        }
    }

    fun onListItemClick(currency: CurrencyDomainModel) {
        baseCurrencyObject =
            CurrencyDomainModel(currency.name, baseCurrencyObject.rate)
        baseCurrency.value = baseCurrencyObject
    }
}



