package com.inex.expensetracker.views.addnewtransaction

import androidx.lifecycle.MutableLiveData
import com.inex.expensetracker.base.BaseViewModel
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.repository.TransactionRepository
import kotlinx.coroutines.launch

class AddNewTransactionViewModel(private var transactionRepository: TransactionRepository) :
    BaseViewModel() {

    var insertTransactionData: MutableLiveData<Long> = MutableLiveData()

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