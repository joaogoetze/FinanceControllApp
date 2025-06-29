package com.example.financecontrollapp.model

data class Expense(
    val name: String? = null,
    val value: Double? = null,
    val installment: Boolean = false,
    val installments: Int = 0,
    val beginMonth: String? = null,
)