package com.example.obukebudgetapp.models

class TransactionItem(
    val amount: Float,
    val type: String,
    val dateEpoch: Long,
    val description: String
)