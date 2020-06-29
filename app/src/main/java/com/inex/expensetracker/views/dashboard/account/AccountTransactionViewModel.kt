package com.inex.expensetracker.views.dashboard.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.inex.expensetracker.base.BaseViewModel
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.model.TransactionListItem
import com.inex.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch

class AccountTransactionViewModel : BaseViewModel{

    private var transactionRepository: TransactionRepository

    constructor(application: Application) : super(application) {
        transactionRepository = TransactionRepository.getInstance(application)
    }

    fun getAllByAccountId(accId: Int): LiveData<List<TransactionListItem>> {
        return transactionRepository.getAllByAccountId(accId)
    }

    fun getBalance(accId: Int): LiveData<Double> {
        return transactionRepository.getBalance(accId)
    }

    fun delete(item: TransactionsData) {
        uiScope.launch {
            transactionRepository.delete(item)
        }
    }
}