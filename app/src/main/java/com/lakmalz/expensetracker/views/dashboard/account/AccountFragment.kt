package com.lakmalz.expensetracker.views.dashboard.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakmalz.expensetracker.R
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment()/*, SwipeRefreshLayout.OnRefreshListener*/ {
    private lateinit var transactionAdapter: AccountTransactionAdapter
    private var accountsData: AccountsData? = null

    companion object {
        fun newInstance(accountsData: AccountsData): AccountFragment {
            val frag =
                AccountFragment()
            frag.accountsData = accountsData
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initAdapter()
    }

    private fun initAdapter() {
        transactionAdapter =
            AccountTransactionAdapter()
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
//        transactionAdapter.setDataSet(sampleList())
    }


}
