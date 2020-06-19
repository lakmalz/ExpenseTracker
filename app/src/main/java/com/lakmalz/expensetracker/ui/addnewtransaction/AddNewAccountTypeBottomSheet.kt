package com.lakmalz.expensetracker.ui.addnewtransaction

import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lakmalz.expensetracker.R
import kotlinx.android.synthetic.main.add_new_account_type_bottom_sheet.view.*

class AddNewAccountTypeBottomSheet : BottomSheetDialogFragment() {

    private var quarterSize: Int = 0
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var mainView: View

    companion object {
        const val TAG = "Add component feature"
        fun open(fragmentManager: FragmentManager): AddNewAccountTypeBottomSheet {
            val view = AddNewAccountTypeBottomSheet()
            view.show(fragmentManager, TAG)
            return view
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        mainView = View.inflate(context, R.layout.add_new_account_type_bottom_sheet, null)
        bottomSheet.setContentView(mainView)
        bottomSheetBehavior = BottomSheetBehavior.from<View>(mainView?.parent as View)
        quarterSize = (Resources.getSystem().displayMetrics.heightPixels * 1f).toInt()
        bottomSheetBehavior.peekHeight = quarterSize
        initUI(mainView)

        return bottomSheet
    }

    private fun hideProgress() {
        mainView?.progress_bar?.visibility = View.GONE
    }

    private fun showProgress() {
        mainView?.progress_bar?.visibility = View.VISIBLE
    }

    fun initUI(mainView: View) {
        mainView?.btn_submit?.setOnClickListener {
            val name = mainView.edt_account_name.text.toString().trim()
            if (name.isNotEmpty()) {
                showProgress()
            } else {
                mainView.edt_account_name.error = getString(R.string.account_name_required)
            }
        }
        mainView?.btn_cancel?.setOnClickListener {
            dismiss()
        }
        mainView.edt_account_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text?.length != 0) {
                    mainView.edt_account_name.error = null
                }
            }

        })
    }


}