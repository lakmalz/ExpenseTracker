package com.inex.expensetracker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.appdatabase.AppDatabase
import com.inex.expensetracker.data.local.dao.TransactionCategoryDataDao
import com.inex.expensetracker.data.local.entity.TransactionCategoryData

class TransactionCategoryRepository(applicationContext: Application) {
    private var transactionCategoryDataDao: TransactionCategoryDataDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionCategoryRepository? = null

        fun getInstance(applicationContext: Application): TransactionCategoryRepository {
            return INSTANCE ?: TransactionCategoryRepository(applicationContext)
        }
    }

    init{
        val database: AppDatabase? = AppDatabase.getInstance(applicationContext.applicationContext)
        transactionCategoryDataDao = database!!.getTransactionCategoryDataDao()
    }

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