package com.inex.expensetracker.views.addnewtransaction.selectionlist

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inex.expensetracker.R
import com.inex.expensetracker.base.BaseActivity
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.model.SelectionModel
import com.inex.expensetracker.data.model.SelectionTypes
import com.inex.expensetracker.utils.Constant
import com.inex.expensetracker.utils.Constant.Companion.EXTRAS_ENABLE_ADD_NEW_ACCOUNT
import com.inex.expensetracker.utils.Constant.Companion.EXTRAS_SELECTION_TYPE
import com.inex.expensetracker.utils.Utils.Companion.showMessageWithTwoButtons
import kotlinx.android.synthetic.main.activity_selection_list.*
import kotlinx.android.synthetic.main.fragment_account.rv_list
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectionListActivity : BaseActivity(), View.OnClickListener {

    private val viewModel by viewModel<SelectionListViewModel>()
    private lateinit var selectionListAdapter: SelectionListAdapter
    private var isEnableAddNewAccount = false
     var selectionType: Int? = null

    companion object {
        fun getIntent(
            context: Context,
            isEnableAddNewAccount: Boolean,
            selectionType: Int
        ): Intent {
            val intent = Intent(context, SelectionListActivity::class.java)
            intent.putExtra(EXTRAS_ENABLE_ADD_NEW_ACCOUNT, isEnableAddNewAccount)
            intent.putExtra(EXTRAS_SELECTION_TYPE, selectionType)
            return intent
        }

        fun getIntent(
            context: Context,
            selectionType: Int
        ): Intent {
            val intent = Intent(context, SelectionListActivity::class.java)
            intent.putExtra(EXTRAS_SELECTION_TYPE, selectionType)
            return intent
        }
    }

    private fun getExtras() {
        if (intent.hasExtra(EXTRAS_SELECTION_TYPE)) {
            selectionType = intent.getIntExtra(EXTRAS_SELECTION_TYPE, -1)
        }
        if (intent.hasExtra(EXTRAS_ENABLE_ADD_NEW_ACCOUNT)) {
            isEnableAddNewAccount = intent.getBooleanExtra(EXTRAS_ENABLE_ADD_NEW_ACCOUNT, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_list)
        getExtras()
        initUI()
        getData()
    }

    private fun initAdapter() {
        selectionListAdapter =
            SelectionListAdapter(::onClickItem, ::onLongPressedItem)
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = selectionListAdapter
        }
    }

    private fun getData() {
        when (selectionType) {
            SelectionTypes.ACCOUNT.value -> {
                viewModel.getAllAccounts().observe(this, Observer { accountList ->
                    selectionListAdapter.setDataSet(mappingAccountList(accountList) as ArrayList<SelectionModel>)
                })
            }
            SelectionTypes.INCOME.value -> {
                viewModel.getAllTractionCategory(true).observe(this, Observer { incomeList ->
                    selectionListAdapter.setDataSet(mappingTransactionCategoryList(incomeList) as ArrayList<SelectionModel>)
                })
            }
            SelectionTypes.EXPENSE.value -> {
                viewModel.getAllTractionCategory(false).observe(this, Observer { expenseList ->
                    selectionListAdapter.setDataSet(mappingTransactionCategoryList(expenseList) as ArrayList<SelectionModel>)
                })
            }
        }
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initAdapter()
        txt_title_category.visibility = View.GONE
        btn_save.setOnClickListener(this)
        visibleEditViewGroup()
        when (selectionType) {
            SelectionTypes.ACCOUNT.value -> {
                if (isEnableAddNewAccount) {
                    supportActionBar?.title = getString(R.string.account)
                    edt_name.hint = getString(R.string.enter_account_name)
                } else {
                    supportActionBar?.title = getString(R.string.select_account)
                }
            }
            SelectionTypes.INCOME.value -> {
                supportActionBar?.title = getString(R.string.select_income)
                edt_name.hint = getString(R.string.enter_income_name)
            }
            SelectionTypes.EXPENSE.value -> {
                supportActionBar?.title = getString(R.string.select_expense)
                edt_name.hint = getString(R.string.enter_expense_name)
            }
        }
    }

    private fun setResultItem(item: SelectionModel) {
        val intent = Intent()
        when (selectionType) {
            SelectionTypes.ACCOUNT.value -> {
                val account = AccountsData(item.name, item.isActive)
                account.id = item.id
                intent.putExtra(Constant.EXTRAS_ACCOUNT_ITEM, account)
            }
            SelectionTypes.INCOME.value -> {
                val income = TransactionCategoryData(item.name, item.isActive, true)
                income.categoryId = item.id
                intent.putExtra(Constant.EXTRAS_INCOME_ITEM, income)
            }
            SelectionTypes.EXPENSE.value -> {
                val expense = TransactionCategoryData(item.name, item.isActive, false)
                expense.categoryId = item.id
                intent.putExtra(Constant.EXTRAS_EXPENSE_ITEM, expense)
            }
        }
        setResult(Activity.RESULT_OK, intent)
    }

    override fun onClick(v: View?) {
        insertItem()
    }

     fun insertItem() {
        if (edt_name.text.isNullOrEmpty()) {
            showMessage(getString(R.string.name_is_required))
            return
        }
        insert(edt_name.text.toString())
        edt_name.text.clear()
    }

    /**
     * selection item click event
     */
    private fun onClickItem(item: SelectionModel) {
        if (selectionType == SelectionTypes.ACCOUNT.value && isEnableAddNewAccount) {
            return
        }
        setResultItem(item)
        onBackPressed()
    }

    /**
     * List item long pressed event
     */
    private fun onLongPressedItem(item: SelectionModel) {
        showDialogDeleteUpdateActionConfirmationAlert(item)
    }

    /**
     * show alert get confirmation for update or delete
     */
    private fun showDialogDeleteUpdateActionConfirmationAlert(model: SelectionModel) {
        showMessageWithTwoButtons(this,
            R.string.you_want_to_update_or_delete,
            R.string.btn_delete,
            R.string.btn_update,
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                if (model.isActive) {
                    showMessage(getString(R.string.no_access_delete).plus(model.name))
                } else {
                    deleteDialog(model)
                }
            },
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                updateDialog(model)
            })
    }

    /**
     * trigger item delete confirmation alert
     * delete account item in Database
     */
    private fun deleteDialog(item: SelectionModel) {
        showMessageWithTwoButtons(this,
            R.string.are_you_sure_you_want_to_delete,
            R.string.yes,
            R.string.no,
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                delete(item)
            },
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
    }

    /**
     * trigger delete item each of categories(account, income  and expense)
     */
    private fun delete(item: SelectionModel) {
        when (selectionType) {
            SelectionTypes.ACCOUNT.value  -> {
                val account = AccountsData(item.name, item.isActive)
                account.id = item.id
                viewModel.deleteAccountItem(account)
            }
            SelectionTypes.INCOME.value  -> {
                val account = TransactionCategoryData(item.name, item.isActive, true)
                account.categoryId = item.id
                viewModel.deleteTransactionCategoryItem(account)
            }
            SelectionTypes.EXPENSE.value ->{
                val account = TransactionCategoryData(item.name, item.isActive, false)
                account.categoryId = item.id
                viewModel.deleteTransactionCategoryItem(account)
            }
        }
    }
    /**
     * trigger insert item each of categories(account, income  and expense)
     */
    private fun insert(nameValue: String) {
        when (selectionType) {
            SelectionTypes.ACCOUNT.value  -> {
                val entity = AccountsData(nameValue, false)
                viewModel.insertAccountItem(entity)
            }
            SelectionTypes.INCOME.value  -> {
                val entity = TransactionCategoryData(nameValue, isActive = false, isIncomeCategory = true)
                viewModel.insertTransactionCategoryItem(entity)
            }
            SelectionTypes.EXPENSE.value ->{
                val entity = TransactionCategoryData(nameValue, isActive = false, isIncomeCategory = false)
                viewModel.insertTransactionCategoryItem(entity)
            }
        }
    }

    /**
     * trigger delete item each of categories(account, income  and expense)
     */
    private fun update(item: SelectionModel) {
        when (selectionType) {
            SelectionTypes.ACCOUNT.value  -> {
                val account = AccountsData(item.name, item.isActive)
                account.id = item.id
                viewModel.updateAccountItem(account)
            }
            SelectionTypes.INCOME.value  -> {
                val account = TransactionCategoryData(item.name, item.isActive, true)
                account.categoryId = item.id
                viewModel.updateTransactionCategory(account)
            }
            SelectionTypes.EXPENSE.value ->{
                val account = TransactionCategoryData(item.name, item.isActive, false)
                account.categoryId = item.id
                viewModel.updateTransactionCategory(account)
            }
        }
        showMessage(getString(R.string.item_successfully_updated))
    }

    /**
     * open dialog for edit account type update
     * account item update in Database
     */
    private fun updateDialog(item: SelectionModel) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.update_name))
        val customLayout: View = layoutInflater.inflate(R.layout.custom_dailog_layout, null)
        val editText = customLayout.findViewById<EditText>(R.id.edt_name)
        editText.setText(item.name)
        builder.setView(customLayout)
        builder.setPositiveButton(getString(R.string.update),
            DialogInterface.OnClickListener { dialog, _ ->
                if (editText.text.isNullOrEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.name_is_required),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    item.name = editText.text.toString()
                    update(item)
                    dialog.dismiss()
                }
            })
        builder.setNegativeButton(
            getString(R.string.cancel),
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /**
     * mapping account list to SelectionList
     */
    private fun mappingAccountList(accountList: List<AccountsData>) = accountList.map {
        it.name?.let { name ->
            it.isActive?.let { isActive ->
                SelectionModel(
                    it.id,
                    name,
                    isActive
                )
            }
        }
    }

    /**
     * mapping transaction category list to SelectionList
     */
    private fun mappingTransactionCategoryList(catList: List<TransactionCategoryData>) = catList.map {
        it.name?.let { name ->
            it.isActive?.let { isActive ->
                SelectionModel(
                    it.categoryId,
                    name,
                    isActive
                )
            }
        }
    }

    private fun visibleEditViewGroup() {
        if (selectionType == SelectionTypes.ACCOUNT.value && isEnableAddNewAccount) {
            edt_view_group.visibility = View.VISIBLE
        } else if (selectionType == SelectionTypes.ACCOUNT.value && !isEnableAddNewAccount) {
            edt_view_group.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
