package com.inex.expensetracker.data.model

import androidx.room.Embedded
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData

class TransactionListItem {
    @Embedded
    lateinit var transaction: TransactionsData
    @Embedded
    lateinit var expenseData: TransactionCategoryData
}