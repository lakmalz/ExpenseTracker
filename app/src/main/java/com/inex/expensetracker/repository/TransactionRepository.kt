package com.inex.expensetracker.repository

import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.dao.AccountDataDao
import com.inex.expensetracker.data.local.dao.TransactionCategoryDataDao
import com.inex.expensetracker.data.local.dao.TransactionsDataDao
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.model.TransactionListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionRepository(private var transactionsDataDao: TransactionsDataDao, private var accountDataDao: AccountDataDao, private var transactionCategoryDataDao: TransactionCategoryDataDao) {


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
        CoroutineScope(Dispatchers.IO).launch {
            accountDataDao.update(item)
        }
    }

    fun updateTransactionCategoryType(item: TransactionCategoryData?) {
        CoroutineScope(Dispatchers.IO).launch {
            transactionCategoryDataDao.update(item)
        }
    }
}