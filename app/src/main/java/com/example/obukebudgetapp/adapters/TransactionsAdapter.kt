package com.example.obukebudgetapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.obukebudgetapp.R
import com.example.obukebudgetapp.models.TransactionItem

class TransactionsAdapter(private val context:Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    inner class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val amountText :TextView =  itemView.findViewById(R.id.amountOfMoneyTextView)
        val dateText :TextView =  itemView.findViewById(R.id.dateTextView)
        val typeText : TextView =  itemView.findViewById(R.id.typeTextView)

        fun bind(transactionItem: TransactionItem) {
            amountText.text = "${transactionItem.amount}"
            typeText.text = transactionItem.type
            dateText.text = transactionItem.epochTimeFormatted
        }
    }

    inner class LoanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val amountText :TextView =  itemView.findViewById(R.id.amountOfMoneyTextView)
        val dateText :TextView =  itemView.findViewById(R.id.dateTextView)
        val typeText : TextView =  itemView.findViewById(R.id.typeTextView)

        fun bind(transactionItem: TransactionItem) {
            amountText.text = "${transactionItem.amount}"
            typeText.text = transactionItem.type
            dateText.text = transactionItem.epochTimeFormatted
        }
    }

    inner class IncomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val amountText :TextView =  itemView.findViewById(R.id.amountOfMoneyTextView)
        val dateText :TextView =  itemView.findViewById(R.id.dateTextView)
        val typeText : TextView =  itemView.findViewById(R.id.typeTextView)

        fun bind(transactionItem: TransactionItem) {
            amountText.text = "${transactionItem.amount}"
            typeText.text = transactionItem.type
            dateText.text = transactionItem.epochTimeFormatted
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = View(context)
        when (viewType) {
            0 -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_list_expense_item, parent, false)
                return ExpenseViewHolder(view)
            }
            1 -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_list_loan_item, parent, false)
                return LoanViewHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_list_income_item, parent, false)
                return IncomeViewHolder(view)
            }
        }
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transaction = differ.currentList[position]
        when(differ.currentList[position].type) {
            "expense" -> (holder as ExpenseViewHolder).bind(transaction)
            "loan" -> (holder as LoanViewHolder).bind(transaction)
            "income" -> (holder as IncomeViewHolder).bind(transaction)
        }
    }

    private val transactionCallback = object: DiffUtil.ItemCallback<TransactionItem>() {
        override fun areItemsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean {
            return oldItem.dateEpoch == newItem.dateEpoch
        }

        override fun areContentsTheSame(
            oldItem: TransactionItem,
            newItem: TransactionItem
        ): Boolean {
            return oldItem.amount == newItem.amount
        }
    }

    private val differ: AsyncListDiffer<TransactionItem> = AsyncListDiffer(this, transactionCallback)

    fun setData(newListOfTransactions: List<TransactionItem>){
        differ.submitList(newListOfTransactions.toMutableList())
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(differ.currentList[position].type) {
            "expense" -> 0
            "loan" -> 1
            "income" -> 2
            else -> -1
        }
    }
}