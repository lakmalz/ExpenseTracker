package com.lakmalz.expensetracker.views.addnewtransaction.incomecategory

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
import com.lakmalz.expensetracker.utils.Constant
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.fragment_account.rv_list

class IncomeSelectionListActivity : AppCompatActivity() {

    private lateinit var viewModel: IncomeListViewModel
    private lateinit var accountListAdapter: IncomeListAdapter

    companion object {
        fun getIntent(context: Context) = Intent(context, IncomeSelectionListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)
        viewModel = ViewModelProvider(this).get(IncomeListViewModel::class.java)
        initUI()
    }

    private fun initAdapter() {
        accountListAdapter =
            IncomeListAdapter(
                ::onClickItem
            )
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountListAdapter
        }
        viewModel.getAll().observe(this, Observer {
            accountListAdapter.setDataSet(it as ArrayList<IncomeCatData>)
        })
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.please_select)
        initAdapter()
        txt_title_category.text = getString(R.string.income)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun onClickItem(item: IncomeCatData) {
        setResult(item)
        onBackPressed()
    }

    private fun setResult(item: IncomeCatData) {
        val intent = Intent()
        intent.putExtra(Constant.EXTRAS_INCOME_ITEM, item)
        setResult(Activity.RESULT_OK, intent)
    }
}
