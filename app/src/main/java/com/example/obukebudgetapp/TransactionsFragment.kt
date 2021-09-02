package com.example.obukebudgetapp

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

        val amountOfMoney = sharedPreferences.getFloat("AMOUNT_OF_MONEY", -1f)

        transactionsAdapter = TransactionsAdapter(requireContext())
        transactionsRecycler.adapter = transactionsAdapter
        transactionsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        amountOfMoneyTextView.text = "$amountOfMoney"
        addButton.setOnClickListener{
            Log.e("addButtonOnClick", "123123")
            val intent = Intent(requireContext(), AddBudgetItemActivity::class.java)
            startActivityForResult(intent, TRANSACTIONS_REQUEST_CODE)
        }

        val stringJson = sharedPreferences.getString("TRANSACTION_LIST", "")
        Log.e("stringJson", "saved value: $stringJson")
        stringJson?.let {
            if(it.isNotEmpty()) {
                val type: Type = object : TypeToken<List<TransactionItem?>?>() {}.type
                transactionList = Gson().fromJson(stringJson, type)
                refreshRecyclerView()
            }
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
                val message = if(transactionResult.type.toLowerCase()=="expense") "Potrosili ste" else "Zaradili ste"
                val notificationMessage = "$message ${transactionResult.amount}€"
                sendNotificationAboutTransaction(transactionResult.type, notificationMessage)
            }
        }
    }

    private fun sendNotificationAboutTransaction(notificationTitle: String, notificationMessage: String) {
        val notificationBuilder = NotificationCompat.Builder(
            requireContext(), createChannelId()
        ).setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setSmallIcon(R.drawable.icon_profile)
            .setAutoCancel(true)
            .setTimeoutAfter(5000)

        NotificationManagerCompat.from(requireContext()).notify(5, notificationBuilder.build())
    }
    private fun createChannelId(): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "transaction_channel_id",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = NotificationManagerCompat.from(requireContext())
            notificationManager.createNotificationChannel(channel)
            channel.id
        } else {
            ""
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