package com.example.obukebudgetapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class TestFragmentOne: Fragment(R.layout.fragment_test_one) {
    lateinit var testTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testTextView = view.findViewById(R.id.titleTextView)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preference_file_name), Context.MODE_PRIVATE)
        val amountOfMoney = sharedPreferences.getInt("AMOUNT_OF_MONEY", -1)

        testTextView.text = "$amountOfMoney"
        testTextView.setOnClickListener{
            val intent = Intent(requireContext(), AddBudgetItemActivity::class.java)
            requireContext().startActivity(intent)
        }
    }
}