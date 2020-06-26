package com.inex.expensetracker.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.model.TransactionListItem

@Dao
interface TransactionsDataDao {
    @Query("SELECT * FROM TransactionsData")
    fun getAll(): LiveData<List<TransactionsData>>

    @Query("SELECT TransactionsData.id,TransactionsData.accId,TransactionsData.catId,TransactionsData.catName, TransactionsData.isIncome, TransactionsData.amount, TransactionsData.currency, TransactionsData.timestamp, TransactionsData.accId, TransactionCategoryData.categoryId, TransactionCategoryData.name FROM TransactionsData  INNER join TransactionCategoryData ON TransactionsData.catId = TransactionCategoryData.categoryId WHERE TransactionsData.accId =:accId  ORDER BY timestamp DESC")
    fun getAllByAccountId(accId: Int): LiveData<List<TransactionListItem>>

    @Query("SELECT SUM(amount) FROM TransactionsData WHERE accId =:accId")
    fun getBalance(accId: Int): LiveData<Double>

    @Insert
    fun insert(transactionsData: TransactionsData)

    @Delete
    fun delete(transactionsData: TransactionsData)
}