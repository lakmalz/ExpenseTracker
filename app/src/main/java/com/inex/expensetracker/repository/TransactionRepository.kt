package com.inex.expensetracker.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.dao.TransactionsDataDao
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.model.TransactionListItem

class TransactionRepository(private var transactionsDataDao: TransactionsDataDao) {

    /*private var accountDataDao: AccountDataDao
    private var transactionCategoryDataDao: TransactionCategoryDataDao*/

    fun insert(entity: TransactionsData) : Long =  transactionsDataDao.insert(entity)

    fun getAll(): LiveData<List<TransactionsData>> = transactionsDataDao.getAll()

    fun getByTimeStamp(timeStamp: Long): TransactionsData = transactionsDataDao.getByTimeStamp(timeStamp)

    fun getAllByAccountId(accId: Int): LiveData<List<TransactionListItem>> {
        return transactionsDataDao.getAllByAccountId(accId)
    }

    fun getBalance(accId: Int): LiveData<Double> {
        return transactionsDataDao.getBalance(accId)
    }

    suspend fun delete(item: TransactionsData) {
            transactionsDataDao.delete(item)
    }

    fun updateAccountType(item: AccountsData) {
        AsyncTask.execute {
//            accountDataDao.update(item)
        }
    }

    fun updateTransactionCategoryType(item: TransactionCategoryData?) {
        AsyncTask.execute {
//            transactionCategoryDataDao.update(item)
        }
    }
}