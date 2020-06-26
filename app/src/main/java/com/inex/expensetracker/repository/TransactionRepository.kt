package com.inex.expensetracker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.appdatabase.AppDatabase
import com.inex.expensetracker.data.local.dao.AccountDataDao
import com.inex.expensetracker.data.local.dao.TransactionCategoryDataDao
import com.inex.expensetracker.data.local.dao.TransactionsDataDao
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.model.TransactionListItem

class TransactionRepository(applicationContext: Application) {
    private  var transactionsDataDao: TransactionsDataDao
    private  var accountDataDao: AccountDataDao
    private  var transactionCategoryDataDao: TransactionCategoryDataDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null

        fun getInstance(applicationContext: Application): TransactionRepository {
            return INSTANCE ?: TransactionRepository(applicationContext)
        }
    }

    init {
        val database: AppDatabase? = AppDatabase.getInstance(applicationContext.applicationContext)
        transactionsDataDao = database!!.getTransactionsDataDao()
        accountDataDao = database.getAccountsDataDao()
        transactionCategoryDataDao = database.getTransactionCategoryDataDao()
    }

    fun insert(entity: TransactionsData) {
        AsyncTask.execute {
            transactionsDataDao.insert(entity)
        }
    }

    fun getAll(): LiveData<List<TransactionsData>> {
        return transactionsDataDao.getAll()
    }

    fun getAllByAccountId(accId: Int): LiveData<List<TransactionListItem>> {
        return transactionsDataDao.getAllByAccountId(accId)
    }

    fun getBalance(accId: Int): LiveData<Double> {
        return transactionsDataDao.getBalance(accId)
    }

    fun delete(item: TransactionsData) {
        AsyncTask.execute {
            transactionsDataDao.delete(item)
        }
    }

    fun updateAccountType(item: AccountsData) {
        AsyncTask.execute {
            accountDataDao.update(item)
        }
    }

    fun updateTransactionCategoryType(item: TransactionCategoryData?) {
        AsyncTask.execute {
            transactionCategoryDataDao.update(item)
        }
    }
}