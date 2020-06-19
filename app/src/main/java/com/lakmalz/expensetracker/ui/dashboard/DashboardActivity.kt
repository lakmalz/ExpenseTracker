package com.lakmalz.expensetracker.ui.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.base.BaseActivity
import com.lakmalz.expensetracker.ui.addnewtransaction.AddNewTransactionActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {

    val titleList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSampleData()// TODO remove line
        initUI()
    }

    private fun setSampleData() {
        titleList.add("BOC")
        titleList.add("HNB")
        titleList.add("COMMERCIAL BANK")
        titleList.add("CASH")
        titleList.add("CREDIT CARD")
        titleList.add("PUBLIC BANK")
    }

    private fun initUI() {
        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_dashboard)
        setTabBar()
    }

    private fun setTabBar() {
        val tabAdapter = AccountTabAdapter(this)
        view_pager.adapter = tabAdapter
        TabLayoutMediator(tab_layout, view_pager) { tab: TabLayout.Tab, position: Int ->
            tab.text = titleList[position]
        }.attach()
    }

    inner class AccountTabAdapter(activity: BaseActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = titleList.size

        override fun createFragment(position: Int): Fragment {
            return AccountFragment.newInstance(titleList[position])
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add-> {
                startActivity(AddNewTransactionActivity.getIntent(this))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

}
