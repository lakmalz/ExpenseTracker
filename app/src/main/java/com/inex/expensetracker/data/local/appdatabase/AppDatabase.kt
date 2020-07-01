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
import com.inex.expensetracker.utils.PrePopulateData.Companion.ACC_BANK_ACCOUNT
import com.inex.expensetracker.utils.PrePopulateData.Companion.ACC_CASH
import com.inex.expensetracker.utils.PrePopulateData.Companion.ACC_CREDIT_CARD
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXPENSE
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_ENTERTAINMENT
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_GROCERY
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_GYM
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_HEALTH
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_TAX
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME_DIVIDENDS
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME_SALARY

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
        private const val databaseName = "expense_tracker"

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, databaseName
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
                accountsDAO?.insert(AccountsData(ACC_CASH, false))
                accountsDAO?.insert(AccountsData(ACC_CREDIT_CARD, false))
                accountsDAO?.insert(AccountsData(ACC_BANK_ACCOUNT, false))
                // Expenses
                transactionCategoryDAO?.insert(TransactionCategoryData(EXP_TAX, false, isIncomeCategory = EXPENSE))
                transactionCategoryDAO?.insert(TransactionCategoryData(EXP_GROCERY, false, isIncomeCategory = EXPENSE))
                transactionCategoryDAO?.insert(TransactionCategoryData(EXP_ENTERTAINMENT, false, isIncomeCategory = EXPENSE))
                transactionCategoryDAO?.insert(TransactionCategoryData(EXP_GYM, false, isIncomeCategory = EXPENSE))
                transactionCategoryDAO?.insert(TransactionCategoryData(EXP_HEALTH, false, isIncomeCategory = EXPENSE))
                // Income
                transactionCategoryDAO?.insert(TransactionCategoryData(INCOME_SALARY, false, isIncomeCategory = INCOME))
                transactionCategoryDAO?.insert(TransactionCategoryData(INCOME_DIVIDENDS, false, isIncomeCategory = INCOME))
            }
        }
    }
}