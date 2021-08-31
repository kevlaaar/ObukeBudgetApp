package com.example.obukebudgetapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.obukebudgetapp.models.TransactionItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddBudgetItemActivity : AppCompatActivity(R.layout.activity_add_budget_item) {

    lateinit var spinnerSelection: String
    lateinit var accountBalance: TextView
    lateinit var amountEditText: TextInputEditText
    lateinit var amountTextLayout: TextInputLayout
    lateinit var confirmButton: Button
    var amountOfMoney: Float = -1f
    var transactionAmount: Float = -1f
    lateinit var transactionItem: TransactionItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val spinner: Spinner = findViewById(R.id.budgetSpinner)
        val budgetTypes = resources.getStringArray(R.array.budget_types)
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, budgetTypes)
        val sharedPreferences = getSharedPreferences(
            getString(R.string.shared_preference_file_name),
            Context.MODE_PRIVATE
        )
        accountBalance = findViewById(R.id.accountBalanceTextView)
        amountOfMoney = sharedPreferences.getFloat("AMOUNT_OF_MONEY", -1f)
        accountBalance.text = "${accountBalance.text} $amountOfMoney"
        amountEditText = findViewById(R.id.amountEditText)
        amountTextLayout = findViewById(R.id.amountTextLayout)
        confirmButton = findViewById(R.id.confirmTransactionButton)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.e("SPINNER SELECTION", "position $position")
                spinnerSelection = budgetTypes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        amountEditText.addTextChangedListener(textWatcher)

        confirmButton.setOnClickListener{
            when(spinnerSelection) {
                "Expense" -> {
                    val amountOfMoney = amountOfMoney - transactionAmount
                    sharedPreferences.edit().putFloat("AMOUNT_OF_MONEY", amountOfMoney).apply()
                    transactionItem = TransactionItem(transactionAmount, "expense", System.currentTimeMillis(), "")
                    val resultIntent = Intent()
                    resultIntent.putExtra("TRANSACTION_RESULT", transactionItem)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
                "Loan" -> {

                }
                "Income" -> {
                    val amountOfMoney = amountOfMoney + transactionAmount
                    sharedPreferences.edit().putFloat("AMOUNT_OF_MONEY", amountOfMoney).apply()
                    transactionItem = TransactionItem(transactionAmount, "income", System.currentTimeMillis(), "")
                    val resultIntent = Intent()
                    resultIntent.putExtra("TRANSACTION_RESULT", transactionItem)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }

    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isNotEmpty()) {
                val number = s.toString().toFloat()
                if (spinnerSelection == "Expense") {
                    if (number > amountOfMoney) {
                        amountTextLayout.isErrorEnabled = true
                        amountTextLayout.error = "You can't spend that amount of money."
                    } else {
                        amountTextLayout.isErrorEnabled = false
                        transactionAmount = number
                    }
                } else {
                    transactionAmount = number
                }
            }
        }
    }

}