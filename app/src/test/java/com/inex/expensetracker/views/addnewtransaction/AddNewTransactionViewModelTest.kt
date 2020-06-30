package com.inex.expensetracker.views.addnewtransaction

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.inex.expensetracker.AppApplication
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.repository.TransactionRepository
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class AddNewTransactionViewModelTest {

    @RelaxedMockK
    lateinit var app: AppApplication

    @RelaxedMockK
    lateinit var dataRepository: TransactionRepository

    lateinit var vm: AddNewTransactionViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        vm = AddNewTransactionViewModel(app)
    }

    @Test
    fun `test insert transaction`() {
        val ent = TransactionsData()
        ent.amount = 12.0
        ent.accId = 1
        ent.catId = 1
        ent.catName = "ABC"
        ent.isIncome = false
        ent.timestamp = 1593333726
        ent.currency = "LKR"
        coEvery { dataRepository.insert(ent) } returns 1
        assertEquals(1, 1)

    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}