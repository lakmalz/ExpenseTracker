package com.inex.expensetracker.views.addnewtransaction.selectionlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.repository.AccountRepository
import com.inex.expensetracker.repository.TransactionCategoryRepository

class SelectionListViewModel(application: Application) : AndroidViewModel(application) {
    private var accountRepository: AccountRepository = AccountRepository.getInstance(application)
    private var transactionCategoryRepository: TransactionCategoryRepository = TransactionCategoryRepository.getInstance(application)

    fun insertAccountItem(entity: AccountsData) {
        accountRepository.insert(entity)
    }

    fun insertTransactionCategoryItem(entity: TransactionCategoryData) {
        transactionCategoryRepository.insert(entity)
    }

    fun deleteAccountItem(entity: AccountsData) {
        accountRepository.delete(entity)
    }
    fun deleteTransactionCategoryItem(entity: TransactionCategoryData) {
        transactionCategoryRepository.delete(entity)
    }

    fun updateAccountItem(entity: AccountsData) {
        accountRepository.update(entity)
    }

    fun updateTransactionCategory(entity: TransactionCategoryData) {
        transactionCategoryRepository.update(entity)
    }

    fun getAllAccounts(): LiveData<List<AccountsData>> {
        return accountRepository.getAll()
    }

    fun getAllTractionCategory(isIncomeCat: Boolean): LiveData<List<TransactionCategoryData>> {
        return transactionCategoryRepository.getAll(isIncomeCat)
    }
}