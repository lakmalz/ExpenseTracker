package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lakmalz.expensetracker.data.db.entity.TransactionsData

@Dao
interface TransactionsDataDao {
    @Query("SELECT * FROM TransactionsData")
    fun getAll(): LiveData<List<TransactionsData>>

    @Insert
    fun insert(transactionsData: TransactionsData)

    @Delete
    fun delete(transactionsData: TransactionsData)
}