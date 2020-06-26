package com.inex.expensetracker.views.addnewtransaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.repository.TransactionRepository

class AddNewTransactionViewModel : AndroidViewModel {

    private var transactionRepository: TransactionRepository

    constructor(application: Application) : super(application) {
        transactionRepository = TransactionRepository.getInstance(application)
    }

    fun insert(entity: TransactionsData) {
        transactionRepository.insert(entity)
    }

    fun updateAccountType(entity: AccountsData) {
        transactionRepository.updateAccountType(entity)
    }

    fun updateTransactionCategoryType(entity: TransactionCategoryData) {
        transactionRepository.updateTransactionCategoryType(entity)
    }
}