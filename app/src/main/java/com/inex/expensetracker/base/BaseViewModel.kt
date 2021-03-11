package com.inex.expensetracker.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel() : ViewModel() {
    private val viewModelJob = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}