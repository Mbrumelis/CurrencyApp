package com.example.currencyapp.network

import java.math.BigDecimal

data class ExchangeRateResponseDTO(
    val rates: Map<String, BigDecimal>,
    val base: String
)