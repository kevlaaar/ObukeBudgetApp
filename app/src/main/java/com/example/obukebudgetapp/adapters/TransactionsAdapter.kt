package com.example.obukebudgetapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.obukebudgetapp.R
import com.example.obukebudgetapp.models.TransactionItem

class TransactionsAdapter(private val context:Context, private val transactionList: List<TransactionItem>):RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {



    inner class TransactionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val amountText :TextView =  itemView.findViewById(R.id.amountOfMoneyTextView)
        val dateText :TextView =  itemView.findViewById(R.id.dateTextView)
        val typeText : TextView =  itemView.findViewById(R.id.typeTextView)

        fun bind(transactionItem: TransactionItem) {
            amountText.text = "$transactionItem.amount"
            typeText.text = transactionItem.type
            dateText.text = "${transactionItem.dateEpoch}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_budget_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}