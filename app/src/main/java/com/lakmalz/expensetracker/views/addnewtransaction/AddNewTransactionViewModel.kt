package com.lakmalz.expensetracker.views.addnewtransaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.TransactionsData
import com.lakmalz.expensetracker.repository.TransactionRepository

class AddNewTransactionViewModel : AndroidViewModel {
    private lateinit var transactionRepository: TransactionRepository
    constructor(application: Application) : super(application) {
        transactionRepository = TransactionRepository.getInstance(application)
    }

    fun insert(entity: TransactionsData) {
        transactionRepository.insert(entity)
    }

    fun getAll(): LiveData<List<TransactionsData>> {
        return transactionRepository.getAll()
    }
}