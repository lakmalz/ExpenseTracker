package com.inex.expensetracker.repository

import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.dao.TransactionCategoryDataDao
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionCategoryRepository(private var transactionCategoryDataDao: TransactionCategoryDataDao) {

    fun insert(entity: TransactionCategoryData) {
        CoroutineScope(Dispatchers.IO).launch {
            transactionCategoryDataDao.insert(entity)
        }
    }

    fun delete(entity: TransactionCategoryData) {
        CoroutineScope(Dispatchers.IO).launch {
            transactionCategoryDataDao.delete(entity)
        }
    }

    fun update(entity: TransactionCategoryData) {
        CoroutineScope(Dispatchers.IO).launch {
            transactionCategoryDataDao.update(entity)
        }
    }

    fun getAll(isIncomeCat: Boolean): LiveData<List<TransactionCategoryData>>{
        return transactionCategoryDataDao.getAll(isIncomeCat)
    }
}