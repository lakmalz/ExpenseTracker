package com.lakmalz.expensetracker.ui.addnewtransaction

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.base.BaseActivity
import com.lakmalz.expensetracker.data.TransactionTypes
import com.lakmalz.expensetracker.utils.Utils
import kotlinx.android.synthetic.main.activity_add_new_transaction.*


class AddNewTransactionActivity : BaseActivity() {

    var expenseType = TransactionTypes.EXPENSE.name

    companion object {
        fun getIntent(context: Context) = Intent(context, AddNewTransactionActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_transaction)
        initUI()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_add_transaction)
        setTransactionType()
        btn_add_new_account_type.setOnClickListener {
            AddNewAccountTypeBottomSheet.open(supportFragmentManager)
        }
        btn_add_new_category_type.setOnClickListener {
            AddNewAccountTypeBottomSheet.open(supportFragmentManager)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                //save transaction
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_transaction_menu, menu)
        return true
    }

    private fun setTransactionType() {
        val chipExpense = Chip(this)
        val chipIncome = Chip(this)
        Utils.customWeight.setMargins(getPixelsFromDp(this, 8f), 0, 0, 0)
        chipIncome.layoutParams = Utils.customWeight
        chipIncome.chipBackgroundColor = ContextCompat.getColorStateList(this, R.color.bg_chip)
        chipIncome.setTextColor(ContextCompat.getColorStateList(this, R.color.text_chip))
        chipIncome.text = getString(R.string.income)
        chipIncome.isFocusable = true
        chipIncome.isClickable = true
        chipIncome.textSize = 14.0f
        chipIncome.isCheckable = true
        chipIncome.checkedIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_check_circle_black_24dp)
        chipIncome.setOnClickListener {
            chipExpense.isChecked = false
            expenseType = TransactionTypes.INCOME.name
        }

        chipExpense.layoutParams = Utils.customWeight
        chipExpense.chipBackgroundColor = ContextCompat.getColorStateList(this, R.color.bg_chip)
        chipExpense.setTextColor(ContextCompat.getColorStateList(this, R.color.text_chip))
        chipExpense.text = getString(R.string.expense)
        chipExpense.isFocusable = true
        chipExpense.isClickable = true
        chipExpense.textSize = 14.0f
        chipExpense.isCheckable = true
        chipExpense.isChecked = true
        chipExpense.checkedIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_check_circle_black_24dp)
        chipExpense.setOnClickListener {
            chipIncome.isChecked = false
            expenseType = TransactionTypes.EXPENSE.name
        }

        chip_group_transaction_types.addView(chipExpense)
        chip_group_transaction_types.addView(chipIncome)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
