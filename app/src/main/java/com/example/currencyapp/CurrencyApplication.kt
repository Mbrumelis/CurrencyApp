package com.example.currencyapp

import android.app.Application
import com.example.currencyapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyapp.data.retrofit.CurrencyApiService
import com.example.currencyapp.data.retrofit.getMockApiService
import com.example.currencyapp.data.retrofit.getMockRetrofit
import com.example.currencyapp.domain.repository.CurrencyRepository
import com.example.currencyapp.domain.usecases.GetCurrencyListUseCase
import com.example.currencyapp.presentation.currencylist.CurrencyListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.*
import retrofit2.Retrofit

class CurrencyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind<CurrencyRepository>() with singleton { CurrencyRepositoryImpl(instance()) }
        bind<Retrofit>() with singleton { getMockRetrofit() }
        bind<CurrencyApiService>() with singleton { getMockApiService(instance()) }
        bind<CurrencyListViewModelFactory>() with provider { CurrencyListViewModelFactory(instance()) }
        bind() from singleton {GetCurrencyListUseCase(instance())}

    }
}