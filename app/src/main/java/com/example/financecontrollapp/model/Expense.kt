package com.example.financecontrollapp.model

data class Expense(
    val name: String? = null,
    val value: Double = 0.0,
    val installment: Boolean = false,
    val installments: Int = 0,
    val beginMonth: String? = null,
)