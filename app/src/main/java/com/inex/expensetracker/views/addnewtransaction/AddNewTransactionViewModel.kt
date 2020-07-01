package com.inex.expensetracker.views.addnewtransaction

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inex.expensetracker.base.BaseViewModel
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AddNewTransactionViewModel : BaseViewModel {
    private var transactionRepository: TransactionRepository
    var insertTransactionData: MutableLiveData<Long> = MutableLiveData()

    constructor(application: Application) : super(application) {
        transactionRepository = TransactionRepository.getInstance(application)
    }

    fun insert(entity: TransactionsData) {
        uiScope.launch {
            insertTransactionData.postValue(transactionRepository.insert(entity))
        }
    }

    fun updateAccountType(entity: AccountsData) {
        transactionRepository.updateAccountType(entity)
    }

    fun updateTransactionCategoryType(entity: TransactionCategoryData) {
        transactionRepository.updateTransactionCategoryType(entity)
    }

}