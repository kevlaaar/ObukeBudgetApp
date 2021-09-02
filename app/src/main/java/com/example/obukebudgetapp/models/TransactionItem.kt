package com.example.obukebudgetapp.models

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class TransactionItem(
    val amount: Float,
    val type: String,
    val dateEpoch: Long,
    val description: String
): Serializable {
    val epochTimeFormatted: String
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd' 'HH:mm", Locale.getDefault())
            val date = Date(dateEpoch)
            return sdf.format(date)
        }
}