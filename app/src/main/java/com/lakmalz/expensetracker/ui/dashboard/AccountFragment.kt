package com.lakmalz.expensetracker.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lakmalz.expensetracker.R
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment()/*, SwipeRefreshLayout.OnRefreshListener*/ {
    private lateinit var transactionAdapter: TransactionAdapter
    private var accountTypeId: String? = null

    companion object {
        fun newInstance(accountTypeId: String): AccountFragment {
            val frag = AccountFragment()
            frag.accountTypeId = accountTypeId
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
        transactionAdapter = TransactionAdapter()
        rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
        transactionAdapter.setDataSet(sampleList())
    }

    fun sampleList(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        return list
    }

}
