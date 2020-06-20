package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
@Dao
interface IncomeCatDataDao {
    @Query("SELECT * FROM IncomeCatData")
    fun getAll(): LiveData<List<IncomeCatData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountsData: IncomeCatData)
}