package com.example.obukebudgetapp.models

import java.io.Serializable

class TransactionItem(
    val amount: Float,
    val type: String,
    val dateEpoch: Long,
    val description: String
): Serializable