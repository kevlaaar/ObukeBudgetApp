package com.example.obukebudgetapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddBudgetItemActivity: AppCompatActivity(R.layout.activity_add_budget_item) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val spinner: Spinner = findViewById(R.id.budgetSpinner)
        val budgetTypes = resources.getStringArray(R.array.budget_types)
        val stringArray = arrayOf("JEDAN", "DVA", "TRI")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, stringArray)
        spinner.adapter = spinnerAdapter
    }
}