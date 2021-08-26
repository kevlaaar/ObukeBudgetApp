package com.example.obukebudgetapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class TransactionsFragment: Fragment(R.layout.fragment_transactions) {
    lateinit var amountOfMoneyTextView: TextView
    lateinit var addButton: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amountOfMoneyTextView = view.findViewById(R.id.balanceTextView)
        addButton = view.findViewById(R.id.addTransactionButton)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preference_file_name), Context.MODE_PRIVATE)
        val amountOfMoney = sharedPreferences.getFloat("AMOUNT_OF_MONEY", -1f)

        amountOfMoneyTextView.text = "$amountOfMoney"
        addButton.setOnClickListener{
            val intent = Intent(requireContext(), AddBudgetItemActivity::class.java)
            requireContext().startActivity(intent)
        }


    }
}