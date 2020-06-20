package com.lakmalz.expensetracker.views.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.repository.AccountRepository

class DashboardViewModel : AndroidViewModel {
    private lateinit var accountRepository: AccountRepository
    constructor(application: Application) : super(application) {
        accountRepository = AccountRepository.getInstance(application)
    }

    fun insert(entity: AccountsData) {
        accountRepository.insert(entity)
    }

    fun getAll(): LiveData<List<AccountsData>> {
        return accountRepository.getAll()
    }
}