package com.lakmalz.expensetracker.data.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseCatData(
    var name: String?
) :Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(parcel: Parcel) : this(parcel.readString()) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseCatData> {
        override fun createFromParcel(parcel: Parcel): ExpenseCatData {
            return ExpenseCatData(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseCatData?> {
            return arrayOfNulls(size)
        }
    }
}