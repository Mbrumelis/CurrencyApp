package com.example.currencyapp.domain.repository

import com.example.currencyapp.domain.model.CurrencyListDomainModel
import java.math.BigDecimal

internal interface CurrencyRepository {
    suspend fun getCurrencyList(baseCurrency: String, baseValue: BigDecimal): CurrencyListDomainModel?
}