package com.inex.expensetracker.views.addnewtransaction.selectionlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.repository.AccountRepository
import com.inex.expensetracker.repository.TransactionCategoryRepository

class SelectionListViewModel(
    private var accountRepository: AccountRepository,
    private var transactionCategoryRepository: TransactionCategoryRepository
) : ViewModel() {

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