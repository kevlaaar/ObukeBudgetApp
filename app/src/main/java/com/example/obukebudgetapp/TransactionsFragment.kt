package com.example.obukebudgetapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obukebudgetapp.adapters.TransactionsAdapter
import com.example.obukebudgetapp.models.TransactionItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TransactionsFragment: Fragment(R.layout.fragment_transactions) {
    lateinit var amountOfMoneyTextView: TextView
    lateinit var addButton: ImageButton
    lateinit var transactionsRecycler: RecyclerView
    lateinit var transactionsAdapter: TransactionsAdapter
    lateinit var sharedPreferences: SharedPreferences

    val TRANSACTIONS_REQUEST_CODE = 100

    var transactionList = arrayListOf<TransactionItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amountOfMoneyTextView = view.findViewById(R.id.balanceTextView)
        addButton = view.findViewById(R.id.addTransactionButton)
        transactionsRecycler = view.findViewById(R.id.transactionsRecycler)
        sharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preference_file_name), Context.MODE_PRIVATE)
        val stringJson = sharedPreferences.getString("TRANSACTION_LIST", "")

        stringJson?.let {
            if(it.isNotEmpty()) {
                val type: Type = object : TypeToken<List<TransactionItem?>?>() {}.type
                transactionList = Gson().fromJson(stringJson, type)
            }
        }

        val amountOfMoney = sharedPreferences.getFloat("AMOUNT_OF_MONEY", -1f)

        transactionsAdapter = TransactionsAdapter(requireContext(), transactionList)
        transactionsRecycler.adapter = transactionsAdapter
        transactionsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        amountOfMoneyTextView.text = "$amountOfMoney"
        addButton.setOnClickListener{
            Log.e("addButtonOnClick", "123123")
            val intent = Intent(requireContext(), AddBudgetItemActivity::class.java)
            startActivityForResult(intent, TRANSACTIONS_REQUEST_CODE)
        }



        Log.e("onViewCreated", "123123")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == TRANSACTIONS_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.let {
                val transactionResult = it.getSerializableExtra("TRANSACTION_RESULT") as TransactionItem
                transactionList.add(transactionResult)
                transactionList.reverse()
                refreshRecyclerView()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    childFragmentManager.beginTransaction().detach(this).commitNow()
//                    childFragmentManager.beginTransaction().attach(this).commitNow()
//                } else {
//                    childFragmentManager.beginTransaction().detach(this).attach(this).commit()
//                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveTransactionListToSharedPreferences()
    }

    private fun saveTransactionListToSharedPreferences() {
        val gson = Gson()
        val listJson = gson.toJson(transactionList)

        sharedPreferences = requireContext().getSharedPreferences(getString(R.string.shared_preference_file_name), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TRANSACTION_LIST", listJson)
        editor.apply()
    }

    private fun refreshRecyclerView() {
        transactionsAdapter.setData(transactionList)
    }

//    companion object {
//        const val TRANSACTIONS_REQUEST_CODE = 100
//    }
}