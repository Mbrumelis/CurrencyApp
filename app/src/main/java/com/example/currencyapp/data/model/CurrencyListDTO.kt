package com.example.currencyapp.data.model

import com.example.currencyapp.domain.model.CurrencyDomainModel
import com.example.currencyapp.domain.model.CurrencyListDomainModel
import java.math.BigDecimal
import java.math.RoundingMode

internal data class CurrencyListDTO(
    val rates: Map<String, BigDecimal>,
    val base: String
)

internal fun CurrencyListDTO.toDomainModel(baseRate: BigDecimal): CurrencyListDomainModel {

    val currencyList: ArrayList<CurrencyDomainModel> = ArrayList<CurrencyDomainModel>()

    for ((key, value) in this.rates) {
        val currency = CurrencyDomainModel(key, value)
        currency.fullRate = baseRate * value
        val roundedRate =
            BigDecimal(value.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        currency.rateString = "1 ${base} = $roundedRate"
        currencyList.add(currency)
    }

    return CurrencyListDomainModel(currencyList)

}