package com.lakmalz.expensetracker.views.addnewtransaction.incomecategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
import com.lakmalz.expensetracker.repository.IncomeRepository

class IncomeListViewModel : AndroidViewModel {
    private lateinit var incomeRepository: IncomeRepository
    constructor(application: Application) : super(application) {
        incomeRepository = IncomeRepository.getInstance(application)
    }

    fun insert(entity: IncomeCatData) {
        incomeRepository.insert(entity)
    }

    fun getAll(): LiveData<List<IncomeCatData>> {
        return incomeRepository.getAll()
    }
}