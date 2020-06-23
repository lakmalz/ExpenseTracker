package com.inex.expensetracker.views.addnewtransaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inex.expensetracker.data.db.entity.AccountsData
import com.inex.expensetracker.data.db.entity.ExpenseCatData
import com.inex.expensetracker.data.db.entity.IncomeCatData
import com.inex.expensetracker.data.db.entity.TransactionsData
import com.inex.expensetracker.repository.TransactionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddNewTransactionViewModel : AndroidViewModel {
    val subscription = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    private lateinit var transactionRepository: TransactionRepository

    constructor(application: Application) : super(application) {
        transactionRepository = TransactionRepository.getInstance(application)
    }

    fun insert(entity: TransactionsData) {
        transactionRepository.insert(entity)
    }

    fun updateAccountType(entity: AccountsData) {
        transactionRepository.updateAccountType(entity)
    }

    fun updateIncomeCatType(entity: IncomeCatData) {
        transactionRepository.updateIncomeCatType(entity)
    }

    fun updateExpenseCatType(entity: ExpenseCatData) {
        transactionRepository.updateExpenseCatType(entity)
    }
}