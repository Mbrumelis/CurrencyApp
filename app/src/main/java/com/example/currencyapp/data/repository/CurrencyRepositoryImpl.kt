package com.example.currencyapp.data.repository

import com.example.currencyapp.data.model.toDomainModel
import com.example.currencyapp.data.retrofit.CurrencyApiService
import com.example.currencyapp.domain.repository.CurrencyRepository
import java.math.BigDecimal

internal class CurrencyRepositoryImpl(private val currencyApiService: CurrencyApiService): CurrencyRepository{

    override suspend fun getCurrencyList(baseCurrency: String, baseValue: BigDecimal) =
        currencyApiService.getProperties(baseCurrency).toDomainModel(baseValue)

}