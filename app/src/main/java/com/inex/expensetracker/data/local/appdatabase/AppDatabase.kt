package com.inex.expensetracker.data.local.appdatabase

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "expense_tracker"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }

        class PopulateDbAsyncTask(db: AppDatabase?) : AsyncTask<Unit, Unit, Unit>() {
            private val accountsDAO = db?.getAccountsDataDao()
            private val transactionCategoryDAO = db?.getTransactionCategoryDataDao()

            override fun doInBackground(vararg param: Unit) {
                // Accounts
                accountsDAO?.insert(AccountsData("Cash", false))
                accountsDAO?.insert(AccountsData("Credit Card", false))
                accountsDAO?.insert(AccountsData("Bank account", false))
                // Expenses
                transactionCategoryDAO?.insert(TransactionCategoryData("Tax", false, isIncomeCategory = false))
                transactionCategoryDAO?.insert(TransactionCategoryData("Grocery", false, isIncomeCategory = false))
                transactionCategoryDAO?.insert(TransactionCategoryData("Entertainment", false, isIncomeCategory = false))
                transactionCategoryDAO?.insert(TransactionCategoryData("Gym", false, isIncomeCategory = false))
                transactionCategoryDAO?.insert(TransactionCategoryData("Health", false, isIncomeCategory = false))
                // Income
                transactionCategoryDAO?.insert(TransactionCategoryData("Salary", false, isIncomeCategory = true))
                transactionCategoryDAO?.insert(TransactionCategoryData("Dividends", false, isIncomeCategory = true))
            }
        }
    }
}