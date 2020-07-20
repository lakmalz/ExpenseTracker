package com.inex.expensetracker.views.dashboard

import androidx.lifecycle.LiveData
import com.inex.expensetracker.base.BaseViewModel
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.preferences.AppSharedPreferences
import com.inex.expensetracker.repository.AccountRepository
import com.inex.expensetracker.repository.TransactionCategoryRepository
import com.inex.expensetracker.utils.PrePopulateData.Companion.ACC_BANK_ACCOUNT
import com.inex.expensetracker.utils.PrePopulateData.Companion.ACC_CASH
import com.inex.expensetracker.utils.PrePopulateData.Companion.ACC_CREDIT_CARD
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXPENSE
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_ENTERTAINMENT
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_GROCERY
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_GYM
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_HEALTH
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_TAX
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME_DIVIDENDS
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME_SALARY
import kotlinx.coroutines.launch

class DashboardViewModel(private var accountRepository: AccountRepository, private var transactionCategoryRepository: TransactionCategoryRepository,
                         private val sharedPref: AppSharedPreferences) : BaseViewModel() {

    var accountList:List<AccountsData> = emptyList()

    /**
     * Get all account list ascending order by Id (account id )
     */
    fun getAllASC(): LiveData<List<AccountsData>> {
        return accountRepository.getAllASC()
    }

    fun prePopulateData() {
        val dbSaved = sharedPref.getBooleanValue(AppSharedPreferences.PREF_DB_SAVED_PRE_POPULATED)
        if (!dbSaved) {
            uiScope.launch {
                // Accounts
                accountRepository.insert(AccountsData(ACC_CASH, false))
                accountRepository.insert(AccountsData(ACC_CREDIT_CARD, false))
                accountRepository.insert(AccountsData(ACC_BANK_ACCOUNT, false))
                // Expenses
                transactionCategoryRepository.insert(TransactionCategoryData(EXP_TAX, false, isIncomeCategory = EXPENSE))
                transactionCategoryRepository.insert(TransactionCategoryData(EXP_GROCERY, false, isIncomeCategory = EXPENSE))
                transactionCategoryRepository.insert(TransactionCategoryData(EXP_ENTERTAINMENT, false, isIncomeCategory = EXPENSE))
                transactionCategoryRepository.insert(TransactionCategoryData(EXP_GYM, false, isIncomeCategory = EXPENSE))
                transactionCategoryRepository.insert(TransactionCategoryData(EXP_HEALTH, false, isIncomeCategory = EXPENSE))
                // Income
                transactionCategoryRepository.insert(TransactionCategoryData(INCOME_SALARY, false, isIncomeCategory = INCOME))
                transactionCategoryRepository.insert(TransactionCategoryData(INCOME_DIVIDENDS, false, isIncomeCategory = INCOME))

                sharedPref.save(AppSharedPreferences.PREF_DB_SAVED_PRE_POPULATED, true)
            }
        }
    }
}