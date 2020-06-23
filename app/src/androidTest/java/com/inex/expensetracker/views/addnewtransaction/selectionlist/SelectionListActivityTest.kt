package com.inex.expensetracker.views.addnewtransaction.selectionlist

import android.widget.Button
import android.widget.EditText
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.inex.expensetracker.R
import com.inex.expensetracker.data.db.entity.AccountsData
import com.inex.expensetracker.model.SelectionTypes
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.android.synthetic.main.activity_selection_list.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SelectionListActivityTest {

    private var edtName: EditText? = null
    private var btnSave: Button? = null

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<SelectionListActivity> =
        ActivityTestRule(SelectionListActivity::class.java)
    private var mActivity: SelectionListActivity? = null

    @Before
    fun setUp() {
        mActivity = activityTestRule.activity
        edtName = mActivity?.findViewById<EditText>(R.id.edt_name)
        btnSave = mActivity?.findViewById<Button>(R.id.btn_save)
    }

    /**
     * Save expense type transactions when all fields provided
     */
    /*@Test
    fun testAddNewAccountValidate_Null_ReturnSuccess() {
        mActivity?.selectionType = SelectionTypes.INCOME.value
        edtName?.setText("")
//        onView((ViewMatchers.withId(R.id.btn_save)))
//            .perform(ViewActions.click())
    }*/

    @Test
    fun testAddNewIncomeTypeValidate_NotNull_ReturnSuccess() {
        mActivity?.selectionType = SelectionTypes.INCOME.value
        mActivity?.edt_name?.setText("")
        onView((ViewMatchers.withId(R.id.btn_save))).perform(ViewActions.click())
    }

    @After
    fun tearDown() {
        mActivity = null
    }
}
