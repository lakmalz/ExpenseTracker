package com.lakmalz.expensetracker.views.addnewtransaction.selectionlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
import com.lakmalz.expensetracker.repository.AccountRepository
import com.lakmalz.expensetracker.repository.ExpenseRepository
import com.lakmalz.expensetracker.repository.IncomeRepository
import kotlin.math.E

class SelectionListViewModel(application: Application) : AndroidViewModel(application) {
    private var accountRepository: AccountRepository = AccountRepository.getInstance(application)
    private var incomeRepository: IncomeRepository = IncomeRepository.getInstance(application)
    private var expenseRepository: ExpenseRepository = ExpenseRepository.getInstance(application)

    fun insertAccountItem(entity: AccountsData) {
        accountRepository.insert(entity)
    }
    fun insertIncomeItem(entity: IncomeCatData) {
        incomeRepository.insert(entity)
    }

    fun insertExpenseItem(entity: ExpenseCatData) {
        expenseRepository.insert(entity)
    }

    fun deleteAccountItem(entity: AccountsData) {
        accountRepository.delete(entity)
    }
    fun deleteIncomeItem(entity: IncomeCatData) {
        incomeRepository.delete(entity)
    }
    fun deleteExpenseItem(entity: ExpenseCatData) {
        expenseRepository.delete(entity)
    }

    fun updateAccountItem(entity: AccountsData) {
        accountRepository.update(entity)
    }

    fun updateIncomeItem(entity: IncomeCatData) {
        incomeRepository.update(entity)
    }

    fun updateExpenseItem(entity: ExpenseCatData) {
        expenseRepository.update(entity)
    }

    fun getAllAccounts(): LiveData<List<AccountsData>> {
        return accountRepository.getAll()
    }

    fun getAllIncomes(): LiveData<List<IncomeCatData>> {
        return incomeRepository.getAll()
    }

    fun getAllExpenses(): LiveData<List<ExpenseCatData>> {
        return expenseRepository.getAll()
    }
}