package com.inex.expensetracker.repository

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.inex.expensetracker.data.local.appdatabase.AppDatabase
import com.inex.expensetracker.data.local.dao.AccountDataDao
import com.inex.expensetracker.data.local.dao.TransactionCategoryDataDao
import com.inex.expensetracker.data.local.dao.TransactionsDataDao
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.utils.PrePopulateData.Companion.ACC_CASH
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXPENSE
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_ENTERTAINMENT
import com.inex.expensetracker.utils.PrePopulateData.Companion.EXP_GYM
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME_DIVIDENDS
import com.inex.expensetracker.utils.PrePopulateData.Companion.INCOME_SALARY
import com.inex.expensetracker.utils.Utils
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TransactionRepositoryTest {

    private lateinit var accountDataDao: AccountDataDao
    lateinit var transactionCategoryDataDao: TransactionCategoryDataDao
    private lateinit var transactionsDataDao: TransactionsDataDao

    @Before
    fun setUp() {
        val database: AppDatabase? = AppDatabase.getInstance(ApplicationProvider.getApplicationContext())
        accountDataDao = database?.getAccountsDataDao()!!
        transactionCategoryDataDao = database.getTransactionCategoryDataDao()
        transactionsDataDao = database.getTransactionsDataDao()
    }

    @Test
    fun test_insert_transactions_and_check_balance() {
        transactionsDataDao.deleteAll()
        val shouldBeBalance = 599.25

        val entityCashSalaryIncome1000 = getTransactionModel(ACC_CASH, INCOME_SALARY, INCOME, 1000.00)
        val entityCashSalaryIncome500 = getTransactionModel(ACC_CASH, INCOME_DIVIDENDS, INCOME, 500.00)
        val entityCashEntertainmentExpense850 = getTransactionModel(ACC_CASH, EXP_ENTERTAINMENT, EXPENSE, -850.00)
        val entityCashGymExpense5075 = getTransactionModel(ACC_CASH, EXP_GYM, EXPENSE, -50.75)

        val trans1 = transactionsDataDao.insert(entityCashSalaryIncome1000)
        assertEquals(trans1.toInt(), transactionsDataDao.getByID(trans1.toInt()).id)

        val trans2 = transactionsDataDao.insert(entityCashSalaryIncome500)
        assertEquals(trans2.toInt(), transactionsDataDao.getByID(trans2.toInt()).id)

        val trans3 = transactionsDataDao.insert(entityCashEntertainmentExpense850)
        assertEquals(trans3.toInt(), transactionsDataDao.getByID(trans3.toInt()).id)

        val trans4 = transactionsDataDao.insert(entityCashGymExpense5075)
        assertEquals(trans4.toInt(), transactionsDataDao.getByID(trans4.toInt()).id)

        assertEquals(transactionsDataDao.getAccountBalance(getAccountId(ACC_CASH)).toLong() , shouldBeBalance.toLong())

    }

    private fun getAccountId(name: String) = accountDataDao.getAccountByName(name)

    private fun getTransactionCategoryId(name: String, isInCome: Boolean) = transactionCategoryDataDao.getTransactionCatIdByName(name, isInCome)

    private fun getTransactionModel(accountType: String, categoryName: String, transactionType: Boolean, amount: Double) : TransactionsData{
        val entity = TransactionsData()
        entity.catId = getTransactionCategoryId(categoryName, transactionType)
        entity.accId = getAccountId(accountType)
        entity.isIncome = transactionType
        entity.timestamp = System.currentTimeMillis()
        entity.currency = Utils.getLocaleCurrencyCode()
        entity.amount = amount
        return entity
    }

    @After
    fun tearDown() {
    }
}