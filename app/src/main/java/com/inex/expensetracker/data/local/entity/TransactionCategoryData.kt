package com.inex.expensetracker.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionCategoryData(
    var name: String?,
    var isActive: Boolean?,
    var isIncomeCategory: Boolean?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var categoryId: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
        categoryId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(isActive)
        parcel.writeValue(isIncomeCategory)
        parcel.writeInt(categoryId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionCategoryData> {
        override fun createFromParcel(parcel: Parcel): TransactionCategoryData {
            return TransactionCategoryData(parcel)
        }

        override fun newArray(size: Int): Array<TransactionCategoryData?> {
            return arrayOfNulls(size)
        }
    }


}