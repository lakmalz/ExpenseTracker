package com.inex.expensetracker.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
@Dao
interface TransactionCategoryDataDao {
    @Query("SELECT * FROM TransactionCategoryData WHERE isIncomeCategory = :isIncomeCat ORDER BY categoryId DESC")
    fun getAll(isIncomeCat: Boolean = false): LiveData<List<TransactionCategoryData>>

    @Query("SELECT categoryId FROM TransactionCategoryData WHERE isIncomeCategory = :isIncomeCat AND name=:name")
    fun getTransactionCatIdByName(name: String, isIncomeCat: Boolean): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountsData: TransactionCategoryData)

    @Update
    fun update(accountsData: TransactionCategoryData?)

    @Delete
    fun delete(accountsData: TransactionCategoryData)
}