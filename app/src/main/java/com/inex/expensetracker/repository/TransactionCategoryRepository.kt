package com.inex.expensetracker.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.dao.TransactionCategoryDataDao
import com.inex.expensetracker.data.local.entity.TransactionCategoryData

class TransactionCategoryRepository(private var transactionCategoryDataDao: TransactionCategoryDataDao) {

    fun insert(entity: TransactionCategoryData) {
        AsyncTask.execute {
            transactionCategoryDataDao.insert(entity)
        }
    }

    fun delete(entity: TransactionCategoryData) {
        AsyncTask.execute {
            transactionCategoryDataDao.delete(entity)
        }
    }

    fun update(entity: TransactionCategoryData) {
        AsyncTask.execute {
            transactionCategoryDataDao.update(entity)
        }
    }

    fun getAll(isIncomeCat: Boolean): LiveData<List<TransactionCategoryData>>{
        return transactionCategoryDataDao.getAll(isIncomeCat)
    }
}