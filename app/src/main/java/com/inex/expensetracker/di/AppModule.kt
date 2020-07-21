package com.inex.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.inex.expensetracker.data.local.appdatabase.AppDatabase
import com.inex.expensetracker.data.preferences.AppSharedPreferences
import com.inex.expensetracker.repository.AccountRepository
import com.inex.expensetracker.repository.TransactionCategoryRepository
import com.inex.expensetracker.repository.TransactionRepository
import com.inex.expensetracker.views.addnewtransaction.AddNewTransactionViewModel
import com.inex.expensetracker.views.addnewtransaction.selectionlist.SelectionListViewModel
import com.inex.expensetracker.views.dashboard.DashboardViewModel
import com.inex.expensetracker.views.dashboard.account.AccountTransactionViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

var appModule: Module = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, AppDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build()
    }
    
    single { get<AppDatabase>().getAccountsDataDao() }
    single { get<AppDatabase>().getTransactionCategoryDataDao() }
    single { get<AppDatabase>().getTransactionsDataDao() }

    factory { AccountRepository(get()) }
    factory { TransactionRepository(get(), get(), get()) }
    factory { TransactionCategoryRepository(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get(), get(), get()) }
    viewModel { AddNewTransactionViewModel(get()) }
    viewModel { AccountTransactionViewModel(get()) }
    viewModel { SelectionListViewModel(get(), get()) }
}

val sharedPreferencesModule = module {
    single { androidApplication().getSharedPreferences(AppSharedPreferences.PREF_FILE_KEY, Context.MODE_PRIVATE) }
    single { AppSharedPreferences(get()) }
}
