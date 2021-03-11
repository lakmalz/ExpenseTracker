package com.inex.expensetracker.views.addnewtransaction

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.inex.expensetracker.R
import com.inex.expensetracker.data.local.entity.AccountsData
import com.inex.expensetracker.data.local.entity.TransactionCategoryData
import com.inex.expensetracker.data.model.TransactionTypes
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNewTransactionActivityTest {

    private var edtAmount: EditText? = null
    private var edtCategory: EditText? = null
    private var edtAccount: EditText? = null

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<AddNewTransactionActivity> =
        ActivityTestRule(AddNewTransactionActivity::class.java)
    private var mActivity: AddNewTransactionActivity? = null

    @Before
    fun setUp() {
        mActivity = activityTestRule.activity
        edtAccount = mActivity?.findViewById<EditText>(R.id.edt_account)
        edtCategory = mActivity?.findViewById<EditText>(R.id.edt_category)
        edtAmount = mActivity?.findViewById<EditText>(R.id.edt_amount)
    }

    @Test
    fun testSaveIncome_SuccessMessage() {
        val accountModel = AccountsData("Virtual wallet", true)
        accountModel.id = 1

        val transactionCat = TransactionCategoryData("Foods", isActive = true, isIncomeCategory = true)
        transactionCat.categoryId = 1

        testFunctionSaveTransactions(TransactionTypes.INCOME.name,accountModel, transactionCat,0.50 )
    }

    @Test
    fun testSaveExpense_SuccessMessage() {
        val accountModel = AccountsData("Cash", true)
        accountModel.id = 1

        val transactionCat = TransactionCategoryData("Health and fitness", isActive = true, isIncomeCategory = false)
        transactionCat.categoryId = 1

        testFunctionSaveTransactions(TransactionTypes.INCOME.name,accountModel,  transactionCat, 1200.00 )
    }

    @Test
    fun testSaveIncome_Validate_Null_Account_Message() {
        testFunctionSaveTransactions(TransactionTypes.INCOME.name,null, null, 500.00 )
    }

    @Test
    fun testSaveExpense_Validate_Null_Account_Message() {
        testFunctionSaveTransactions(TransactionTypes.EXPENSE.name,null,  null, 8900.00 )
    }

    fun testFunctionSaveTransactions(type: String, accountModel: AccountsData?, transactionCategoryData: TransactionCategoryData?, amount:Double){
        mActivity?.transactionType = type
        edtAccount?.tag = accountModel

        if (type == TransactionTypes.INCOME.name) {
            edtCategory?.tag = transactionCategoryData
        } else {
            edtCategory?.tag = transactionCategoryData
        }

        edtAmount?.setText("$amount")

        onView((withId(R.id.action_done)))
            .perform(click())
    }


    @After
    fun tearDown() {
        mActivity = null
    }
}