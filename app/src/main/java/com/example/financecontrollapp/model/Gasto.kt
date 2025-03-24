package com.example.financecontrollapp.model

data class Gasto(
    val nome: String? = null,
    val valor: Double? = null,
    val parcelado: Boolean = false,
    val parcelas: Int = 0,
    val mesInicio: String? = null,
)