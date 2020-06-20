package com.lakmalz.expensetracker.views.addnewtransaction.selectaccounttype

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.utils.Constant
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.fragment_account.rv_list

class AccountSelectionListActivity : AppCompatActivity() {

    private lateinit var viewModel: AccountSelectionListViewModel
    private lateinit var accountListAdapter: AccountListAdapter

    companion object {
        fun getIntent(context: Context) = Intent(context, AccountSelectionListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)
        viewModel = ViewModelProvider(this).get(AccountSelectionListViewModel::class.java)
        initUI()
    }

    private fun initAdapter() {
        accountListAdapter =
            AccountListAdapter(
                ::onClickItem
            )
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountListAdapter
        }
        viewModel.getAll().observe(this, Observer {
            accountListAdapter.setDataSet(it as ArrayList<AccountsData>)
        })
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.select_account)
        initAdapter()
        txt_title_category.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun onClickItem(item: AccountsData) {
        setResult(item)
        onBackPressed()
    }

    private fun setResult(item: AccountsData) {
        val intent = Intent()
        intent.putExtra(Constant.EXTRAS_ACCOUNT_ITEM, item)
        setResult(Activity.RESULT_OK, intent)
    }
}
