package com.inex.expensetracker.views.addnewtransaction

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.inex.expensetracker.R
import com.inex.expensetracker.base.BaseActivity
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.local.entity.TransactionsData
import com.inex.expensetracker.data.model.SelectionTypes
import com.inex.expensetracker.data.model.TransactionTypes
import com.inex.expensetracker.utils.Constant
import com.inex.expensetracker.utils.Constant.Companion.EXTRAS_ACCOUNT_ITEM
import com.inex.expensetracker.utils.Constant.Companion.EXTRAS_EXPENSE_ITEM
import com.inex.expensetracker.utils.Constant.Companion.EXTRAS_INCOME_ITEM
import com.inex.expensetracker.utils.Constant.Companion.REQUEST_ACCOUNT_LIST
import com.inex.expensetracker.utils.Constant.Companion.REQUEST_EXPENSE_LIST
import com.inex.expensetracker.utils.Constant.Companion.REQUEST_INCOME_LIST
import com.inex.expensetracker.utils.Utils
import com.inex.expensetracker.views.addnewtransaction.selectionlist.SelectionListActivity
import kotlinx.android.synthetic.main.activity_add_new_transaction.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*


class AddNewTransactionActivity : BaseActivity(), View.OnClickListener {

    private val viewModel by viewModel<AddNewTransactionViewModel>()
    var transactionType = TransactionTypes.EXPENSE.name
    var selectedAccount: AccountsData? = null

    companion object {
        fun getIntent(context: Context, accountData: AccountsData): Intent {
            val intent = Intent(context, AddNewTransactionActivity::class.java)
            intent.putExtra(Constant.EXTRAS_ACCOUNT_ITEM, accountData)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_transaction)
        getExtras()
        liveObserveData()
        initUI()
    }

    private fun liveObserveData() {
        viewModel.insertTransactionData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                Utils.showMessage(
                    this,
                    getString(R.string.transaction_added_successful),
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                        onBackPressed()
                    })
            } else {
                showMessage(getString(R.string.fail_msg_transaction_insert))
            }
        })
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_add_transaction)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
        setTransactionTypeItems()
        edt_account.setText(selectedAccount?.name ?: "")
        edt_account.tag = selectedAccount
        edt_account.setOnClickListener(this)
        edt_category.setOnClickListener(this)
        txt_symbol.text = Utils.getCurrencySymbol()
    }

    private fun getExtras() {
        if (intent.hasExtra(EXTRAS_ACCOUNT_ITEM)) {
            selectedAccount = intent.getParcelableExtra<AccountsData>(EXTRAS_ACCOUNT_ITEM)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                if (validation()) {
                    var transactionCat = edt_category.tag as TransactionCategoryData
                    saveTransaction(
                        transactionType,
                        edt_account.tag as AccountsData,
                        transactionCat,
                        edt_amount.text.toString().toDouble()
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * transaction save into Database
     */
    private fun saveTransaction(
        transactionType: String,
        accountModel: AccountsData,
        transactionCategoryData: TransactionCategoryData?,
        amount: Double
    ) {
        val transaction = TransactionsData()
        transaction.accId = accountModel.id

        transaction.catId = transactionCategoryData?.categoryId
        transaction.amount = amount

        when (transactionType) {
            TransactionTypes.INCOME.name -> {
                transaction.isIncome = true
            }
            TransactionTypes.EXPENSE.name -> {
                transaction.isIncome = false
                transaction.amount = -(amount)
            }
        }

        transaction.currency = Utils.getLocaleCurrencyCode()
        transaction.timestamp = System.currentTimeMillis()

        // save new transaction
        viewModel.insert(transaction)

        // transaction category update as a Active category
        transactionCategoryData?.isActive = true
        transactionCategoryData?.let { viewModel.updateTransactionCategoryType(it) }

        // account type update as a Active account type
        accountModel.isActive = true
        viewModel.updateAccountType(accountModel)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_transaction_menu, menu)
        return true
    }

    /**
     * set transaction types view
     */
    private fun setTransactionTypeItems() {
        chip_income.setOnClickListener {
            transactionType = TransactionTypes.INCOME.name
            resetFields()
            edt_category.hint = getString(R.string.select_income)
        }

        chip_expense.setOnClickListener {
            transactionType = TransactionTypes.EXPENSE.name
            resetFields()
            edt_category.hint = getString(R.string.select_expense)
        }
        resetFields()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_ACCOUNT_LIST -> {
                    val item = data?.getParcelableExtra<AccountsData>(EXTRAS_ACCOUNT_ITEM)
                    edt_account.setText(item?.name)
                    edt_account.tag = item
                }
                REQUEST_INCOME_LIST -> {
                    val item = data?.getParcelableExtra<TransactionCategoryData>(EXTRAS_INCOME_ITEM)
                    edt_category.setText(item?.name)
                    edt_category.tag = item
                }
                REQUEST_EXPENSE_LIST -> {
                    val item =
                        data?.getParcelableExtra<TransactionCategoryData>(EXTRAS_EXPENSE_ITEM)
                    edt_category.setText(item?.name)
                    edt_category.tag = item
                }
            }
        }
    }

    /**
     * Reset fields when changing transaction type
     */
    private fun resetFields() {
        var format: NumberFormat = Utils.getCurrencyInstance()
        format.currency = Currency.getInstance(Utils.getLocaleCurrencyCode())
        val placeHolderValue = format.format(1234567890.99)
        edt_category.hint = getString(R.string.select_expense)
        edt_amount.hint = "${getString(R.string.enter_amount)} (${placeHolderValue})"
        edt_amount.text.clear()
        edt_category.text.clear()
        edt_category.tag = null
    }

    /**
     * Validate input fields
     */
    private fun validation(): Boolean {
        when {
            edt_account.tag == null -> {
                showMessage(getString(R.string.account_name_is_required))
                return false
            }
            edt_category.tag == null -> {
                if (transactionType == TransactionTypes.INCOME.name) {
                    showMessage(getString(R.string.income_is_required))
                } else {
                    showMessage(getString(R.string.expense_is_required))
                }
                return false
            }
            edt_amount.text.isNullOrEmpty() -> {
                showMessage(getString((R.string.amount_is_required)))
                return false
            }
            else -> return true
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            edt_category.id -> {
                clickOnCategory()
            }
            edt_account.id -> {
                clickOnAccount()
            }
        }
    }

    /**
     * Open account type selection view
     */
    private fun clickOnAccount() {
        val intent = SelectionListActivity.getIntent(this, false, SelectionTypes.ACCOUNT.value)
        startActivityForResult(intent, REQUEST_ACCOUNT_LIST)
    }

    /**
     * Open transaction selection view
     */
    private fun clickOnCategory() {
        when (transactionType) {
            TransactionTypes.INCOME.name -> {
                val intent = SelectionListActivity.getIntent(this, SelectionTypes.INCOME.value)
                startActivityForResult(intent, REQUEST_INCOME_LIST)
            }
            TransactionTypes.EXPENSE.name -> {
                val intent = SelectionListActivity.getIntent(this, SelectionTypes.EXPENSE.value)
                startActivityForResult(intent, REQUEST_EXPENSE_LIST)
            }
        }
    }
}
