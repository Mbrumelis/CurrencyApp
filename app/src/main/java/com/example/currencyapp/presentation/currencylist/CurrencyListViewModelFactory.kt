package com.example.currencyapp.presentation.currencylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyapp.domain.usecases.GetCurrencyListUseCase

internal class CurrencyListViewModelFactory(private val currencyListUseCase: GetCurrencyListUseCase)
    : ViewModelProvider.NewInstanceFactory(){

    @Suppress ("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyListViewModel(currencyListUseCase) as T
    }
}