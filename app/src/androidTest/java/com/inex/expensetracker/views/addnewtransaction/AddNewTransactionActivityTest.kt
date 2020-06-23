package com.inex.expensetracker.views.addnewtransaction

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.inex.expensetracker.R
import com.inex.expensetracker.data.db.entity.AccountsData
import com.inex.expensetracker.data.db.entity.ExpenseCatData
import com.inex.expensetracker.data.db.entity.IncomeCatData
import com.inex.expensetracker.model.TransactionTypes
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class AddNewTransactionActivityTest {

    private var edtAmount: EditText? = null
    private var edtCategory: EditText? = null
    private var edtAccount: EditText? = null

    @Mock


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

        val incomeCatData = IncomeCatData("Foods", true)
        incomeCatData.id = 1

        testFunctionSaveTransactions(TransactionTypes.INCOME.name,accountModel, incomeCatData, null, 0.50 )
    }

    @Test
    fun testSaveExpense_SuccessMessage() {
        val accountModel = AccountsData("Cash", true)
        accountModel.id = 1

        val expenseCatData = ExpenseCatData("Health and fitness", true)
        expenseCatData.id = 1

        testFunctionSaveTransactions(TransactionTypes.INCOME.name,accountModel, null, expenseCatData, 1200.00 )
    }

    @Test
    fun testSaveIncome_Validate_Null_Account_Message() {
        testFunctionSaveTransactions(TransactionTypes.INCOME.name,null, null, null, 500.00 )
    }

    @Test
    fun testSaveExpense_Validate_Null_Account_Message() {
        testFunctionSaveTransactions(TransactionTypes.EXPENSE.name,null, null, null, 8900.00 )
    }

    fun testFunctionSaveTransactions(type: String, accountModel: AccountsData?, incomeCatData: IncomeCatData?, expenseCatData: ExpenseCatData?, amount:Double){
        mActivity?.transactionType = type
        edtAccount?.tag = accountModel

        if (type == TransactionTypes.INCOME.name) {
            edtCategory?.tag = incomeCatData
        } else {
            edtCategory?.tag = expenseCatData
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