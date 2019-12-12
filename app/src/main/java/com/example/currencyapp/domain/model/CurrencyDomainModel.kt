package com.example.currencyapp.domain.model

import java.math.BigDecimal

data class CurrencyDomainModel (
    var name: String,
    var rate: BigDecimal
) {
    var rateString: String = ""
    var fullRate: BigDecimal = BigDecimal(1.0)
    val fullName: String = when (name) {
        "CAD" -> "Canadian Dollar"
        "HKD" -> "Hong Kong Dollar"
        "ISK" -> "Icelandic Króna"
        "PHP" -> "Philippine Peso"
        "DKK" -> "Danish Krone"
        "HUF" -> "Hungarian Forint"
        "CZK" -> "Czech Koruna"
        "AUD" -> "Australian Dollar"
        "RON" -> "Romanian Leu"
        "SEK" -> "Swedish Krona"
        "IDR" -> "Indonesian Rupiah"
        "INR" -> "Indian Rupee"
        "BRL" -> "Brazilian Real"
        "RUB" -> "Russian Ruble"
        "HRK" -> "Croatian Kuna"
        "JPY" -> "Japanese Yen"
        "THB" -> "Thai Baht"
        "CHF" -> "Swiss Franc"
        "SGD" -> "Singapore Dollar"
        "PLN" -> "Poland złoty"
        "BGN" -> "Bulgarian Lev"
        "TRY" -> "Turkish lira"
        "CNY" -> "Chinese Yuan"
        "NOK" -> "Norwegian Krone"
        "NZD" -> "New Zealand Dollar"
        "ZAR" -> "South African Rand"
        "USD" -> "United States Dollar"
        "MXN" -> "Mexican Peso"
        "ILS" -> "Israeli New Shekel"
        "GBP" -> "Pound Sterling"
        "KRW" -> "South Korean won"
        "MYR" -> "Malaysian Ringgit"
        "EUR" -> "Euro"
        else -> ""
    }
}