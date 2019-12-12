package com.example.currencyapp.domain.usecases

import com.example.currencyapp.domain.model.CurrencyListDomainModel
import com.example.currencyapp.domain.repository.CurrencyRepository
import java.math.BigDecimal

internal class GetCurrencyListUseCase(
    private val currencyRepository: CurrencyRepository
) {
    suspend fun execute(
        baseCurrency: String,
        baseValue: BigDecimal
    ): CurrencyListDomainModel? {
        return currencyRepository.getCurrencyList(baseCurrency, baseValue)

    }
}