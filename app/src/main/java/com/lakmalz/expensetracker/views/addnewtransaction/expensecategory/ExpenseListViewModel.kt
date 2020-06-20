package com.lakmalz.expensetracker.views.addnewtransaction.expensecategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
import com.lakmalz.expensetracker.repository.ExpenseRepository

class ExpenseListViewModel : AndroidViewModel {
    private lateinit var expenseRepository: ExpenseRepository
    constructor(application: Application) : super(application) {
        expenseRepository = ExpenseRepository.getInstance(application)
    }

    fun insert(entity: ExpenseCatData) {
        expenseRepository.insert(entity)
    }

    fun getAll(): LiveData<List<ExpenseCatData>> {
        return expenseRepository.getAll()
    }
}