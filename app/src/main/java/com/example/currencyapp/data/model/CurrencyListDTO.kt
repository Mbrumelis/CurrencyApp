package com.example.currencyapp.data.model

import com.example.currencyapp.domain.model.CurrencyDomainModel
import com.example.currencyapp.domain.model.CurrencyListDomainModel
import java.math.BigDecimal

internal data class CurrencyListDTO(
    val rates: Map<String, BigDecimal>,
    val base: String
)

internal fun CurrencyListDTO.toDomainModel(baseRate: BigDecimal): CurrencyListDomainModel {

    val currencyList: ArrayList<CurrencyDomainModel> = ArrayList()
    val sorted = this.rates.toSortedMap()
    val baseCurrency = CurrencyDomainModel(this.base, baseRate)
    for ((key, value) in sorted) {
        val currency = CurrencyDomainModel(key, value)
        currencyList.add(currency)
    }
    return CurrencyListDomainModel(currencyList, baseCurrency)

}