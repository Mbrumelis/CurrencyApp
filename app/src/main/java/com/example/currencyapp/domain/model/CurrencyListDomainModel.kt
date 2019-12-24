package com.example.currencyapp.domain.model


internal data class CurrencyListDomainModel(
    val currencyList: ArrayList<CurrencyDomainModel>,
    val baseCurrency: CurrencyDomainModel
)

internal fun CurrencyListDomainModel.toViewableList(): ArrayList<CurrencyDomainModel>{
    for (curr in  this.currencyList){
        curr.fullRate = this.baseCurrency.rate * curr.rate
        val roundedRate = String.format("%.2f", curr.rate)
        curr.rateString = "1 ${this.baseCurrency.name} = $roundedRate"
    }
    return this.currencyList
}