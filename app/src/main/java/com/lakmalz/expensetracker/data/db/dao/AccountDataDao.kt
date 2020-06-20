package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lakmalz.expensetracker.data.db.entity.AccountsData
@Dao
interface AccountDataDao {
    @Query("SELECT * FROM AccountsData ORDER BY id ASC")
    fun getAll(): LiveData<List<AccountsData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountsData: AccountsData)
}