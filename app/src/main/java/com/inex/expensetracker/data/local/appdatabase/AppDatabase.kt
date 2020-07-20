package com.inex.expensetracker.data.local.appdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.inex.expensetracker.data.local.dao.AccountDataDao
import com.inex.expensetracker.data.local.dao.TransactionCategoryDataDao
import com.inex.expensetracker.data.local.dao.TransactionsDataDao
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData

@Database(
    entities = [AccountsData::class, TransactionCategoryData::class, TransactionsData::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountsDataDao(): AccountDataDao
    abstract fun getTransactionCategoryDataDao(): TransactionCategoryDataDao
    abstract fun getTransactionsDataDao(): TransactionsDataDao

    companion object {
        const val databaseName = "expense_tracker"
    }
}