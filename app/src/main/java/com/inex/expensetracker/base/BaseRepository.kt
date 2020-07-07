package com.inex.expensetracker.base

import android.content.Context
import com.inex.expensetracker.data.local.appdatabase.AppDatabase

open class BaseRepository(applicationContext: Context) {
    protected var database: AppDatabase? = null

    init {
        database = AppDatabase.getInstance(applicationContext)
    }
}