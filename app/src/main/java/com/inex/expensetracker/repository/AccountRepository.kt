package com.inex.expensetracker.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.dao.AccountDataDao
import com.inex.expensetracker.data.local.entity.AccountsData

class AccountRepository(private var accountDataDAO: AccountDataDao) {

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