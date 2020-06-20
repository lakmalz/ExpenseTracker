package com.lakmalz.expensetracker.views.addnewtransaction.selectaccounttype

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
import com.lakmalz.expensetracker.repository.AccountRepository
import com.lakmalz.expensetracker.repository.ExpenseRepository

class AccountSelectionListViewModel : AndroidViewModel {
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