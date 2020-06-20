package com.lakmalz.expensetracker.views.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lakmalz.expensetracker.base.BaseActivity
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.views.dashboard.account.AccountFragment

class DashboardTabAdapter(activity: BaseActivity) :
    FragmentStateAdapter(activity) {
    private var titleList: ArrayList<AccountsData> = ArrayList()

    fun setTabTitle(titleList: ArrayList<AccountsData>) {
        this.titleList = titleList
    }

    override fun getItemCount(): Int = titleList.size

    override fun createFragment(position: Int): Fragment {
        return AccountFragment.newInstance(titleList[position])
    }
}