package com.lakmalz.expensetracker.ui.dashboard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.utils.inflate

class TransactionAdapter(/*var onItemChecked: (item: String) -> Unit, var onItemDeleteClick: (position: Int, item: String) -> Unit*/) : RecyclerView.Adapter<TransactionAdapter.ItemViewHolder>() {

    private var list: ArrayList<String> = ArrayList()
    fun setDataSet(_list: ArrayList<String>) {
        list.clear()
        list = _list
        notifyDataSetChanged()
    }

    fun removeAt(position: Int?) {
        position?.let {
            list.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_transaction)) {
        fun bind(item: String) = with(itemView) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position])
}