package com.lakmalz.expensetracker.views.addnewtransaction.expensecategory

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
import com.lakmalz.expensetracker.utils.Constant
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.fragment_account.rv_list

class ExpenseSelectionListActivity : AppCompatActivity() {

    private lateinit var viewModel: ExpenseListViewModel
    private lateinit var accountListAdapter: ExpenseListAdapter

    companion object {
        fun getIntent(context: Context) = Intent(context, ExpenseSelectionListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)
        viewModel = ViewModelProvider(this).get(ExpenseListViewModel::class.java)
        initUI()
    }

    private fun initAdapter() {
        accountListAdapter =
            ExpenseListAdapter(
                ::onClickItem
            )
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountListAdapter
        }
        viewModel.getAll().observe(this, Observer {
            accountListAdapter.setDataSet(it as ArrayList<ExpenseCatData>)
        })
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.please_select)
        initAdapter()
        txt_title_category.text = getString(R.string.expense)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun onClickItem(item: ExpenseCatData) {
        setResult(item)
        onBackPressed()
    }

    private fun setResult(item: ExpenseCatData) {
        val intent = Intent()
        intent.putExtra(Constant.EXTRAS_EXPENSE_ITEM, item)
        setResult(Activity.RESULT_OK, intent)
    }
}
