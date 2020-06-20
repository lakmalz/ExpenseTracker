package com.lakmalz.expensetracker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.appdatabase.AppDatabase
import com.lakmalz.expensetracker.data.db.dao.TransactionsDataDao
import com.lakmalz.expensetracker.data.db.entity.TransactionsData

class TransactionRepository(applicationContext: Application) {
    private lateinit var transactionsDataDao: TransactionsDataDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null

        fun getInstance(applicationContext: Application): TransactionRepository {
            return INSTANCE ?: TransactionRepository(applicationContext)
        }
    }

    init{
        val database: AppDatabase? = AppDatabase.getInstance(applicationContext.applicationContext)
        transactionsDataDao = database!!.getTransactionsDataDao()
    }

    fun insert(entity: TransactionsData) {
        AsyncTask.execute {
            transactionsDataDao.insert(entity)
        }
    }

    fun getAll(): LiveData<List<TransactionsData>>{
        return transactionsDataDao.getAll()
    }
}