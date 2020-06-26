package com.inex.expensetracker.views.dashboard.account

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.inex.expensetracker.R
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.model.TransactionListItem
import com.inex.expensetracker.utils.Utils
import com.inex.expensetracker.utils.inflate
import kotlinx.android.synthetic.main.list_item_transaction.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountTransactionAdapter(val onLongPressItem: (item: TransactionsData) -> Unit) :

    RecyclerView.Adapter<AccountTransactionAdapter.ItemViewHolder>() {

    var format: NumberFormat = Utils.getCurrencyInstance()

    private var list: ArrayList<TransactionListItem> = ArrayList()

    fun setDataSet(_list: ArrayList<TransactionListItem>) {
        list.clear()
        list = _list
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_transaction)) {
        fun bind(item: TransactionListItem) = with(itemView) {
            txt_transaction_date.text =
                item.transaction.timestamp?.let { Utils.getFormattedDate(context, it) }
            format.currency = Currency.getInstance(item.transaction.currency)
            val amount = format.format(item.transaction.amount)
            txt_amount.text = "$amount"
            txt_amount.setTextColor(ContextCompat.getColor(context, R.color.red_text))
            if (item.transaction.isIncome) {
                txt_amount.setTextColor(ContextCompat.getColor(context, R.color.green_text))
            }
            txt_transaction_name.text = item.expenseData.name
            itemView.setOnLongClickListener {
                var selectItem = TransactionsData()
                selectItem.id = item.transaction.id
                selectItem.accId = item.transaction.accId
                selectItem.catId = item.transaction.catId
                selectItem.catName = item.transaction.catName
                selectItem.isIncome = item.transaction.isIncome
                selectItem.amount = item.transaction.amount
                selectItem.currency = item.transaction.currency
                selectItem.timestamp = item.transaction.timestamp
                onLongPressItem(selectItem)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position])
}