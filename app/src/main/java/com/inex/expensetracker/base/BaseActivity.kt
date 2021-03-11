package com.inex.expensetracker.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inex.expensetracker.R

open class BaseActivity : AppCompatActivity() {

    protected fun showMessage(message: String = getString(R.string.oops_something_went_wrong)) {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(message)
        alert.show()
    }

}