package com.inex.expensetracker.repository

import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.dao.AccountDataDao
import com.inex.expensetracker.data.local.entity.AccountsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountRepository(private var accountDataDAO: AccountDataDao) {

    fun insert(entity: AccountsData) {
        CoroutineScope(Dispatchers.IO).launch {
            accountDataDAO.insert(entity)
        }
    }

    fun delete(entity: AccountsData) {
        CoroutineScope(Dispatchers.IO).launch {
            accountDataDAO.delete(entity)
        }
    }

    fun update(entity: AccountsData) {
        CoroutineScope(Dispatchers.IO).launch {
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