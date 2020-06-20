package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
@Dao
interface ExpenseCatDataDao {
    @Query("SELECT * FROM ExpenseCatData")
    fun getAll(): LiveData<List<ExpenseCatData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountsData: ExpenseCatData)
}