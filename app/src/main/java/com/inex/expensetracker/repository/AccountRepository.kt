package com.inex.expensetracker.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.inex.expensetracker.base.BaseRepository
import com.inex.expensetracker.data.local.appdatabase.AppDatabase
import com.inex.expensetracker.data.local.dao.AccountDataDao
import com.inex.expensetracker.data.local.entity.AccountsData

class AccountRepository(applicationContext: Context):BaseRepository(applicationContext) {
    private var accountDataDAO: AccountDataDao

    companion object {
        @Volatile
        private var INSTANCE: AccountRepository? = null

        fun getInstance(applicationContext: Application): AccountRepository {
            return INSTANCE ?: AccountRepository(applicationContext)
        }
    }

    init{
        accountDataDAO = database!!.getAccountsDataDao()
    }

    fun insert(entity: AccountsData) {
        AsyncTask.execute {
            accountDataDAO.insert(entity)
        }
    }

    fun delete(entity: AccountsData) {
        AsyncTask.execute {
            accountDataDAO.delete(entity)
        }
    }

    fun update(entity: AccountsData) {
        AsyncTask.execute {
            accountDataDAO.update(entity)
        }
    }

    fun getAll(): LiveData<List<AccountsData>>{
        return accountDataDAO.getAll()
    }

    fun getAllASC(): LiveData<List<AccountsData>>{
        return accountDataDAO.getAllASC()
    }
}