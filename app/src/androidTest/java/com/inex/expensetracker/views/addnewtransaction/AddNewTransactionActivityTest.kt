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
    fun testFunctionSaveTransactions(type: String, accountModel: AccountsData, incomeCatData: IncomeCatData, expenseCatData: ExpenseCatData, amount: Int){

    }

    /**
     * Save expense type transactions when all fields provided
     */
    @Test
    fun testExpenseSaveValidateAccountIncomeCategoryAmount_NotNullInput_ReturnSuccess() {
        var transactionType = TransactionTypes.INCOME.name
        val accountModel = AccountsData("Account name", true)
        accountModel.id = 1

        val expenseCatData = ExpenseCatData("Expense name", true)
        accountModel.id = 1

        edtAccount?.tag = accountModel
        edtCategory?.tag = expenseCatData
        edtAmount?.setText("15002.50")

        onView((withId(R.id.action_done)))
            .perform(click())
    }

    /**
     * Save expense type transactions when all fields provided
     */
    @Test
    fun testExpenseSaveValidateAccountIncomeCategoryAmount_NullInput_ReturnMessage() {
        var transactionType = TransactionTypes.EXPENSE.name
        val accountModel = null

        val expenseCatData = null

        edtAccount?.tag = accountModel
        edtCategory?.tag = expenseCatData
        edtAmount?.setText("15002.50")

        onView((withId(R.id.action_done)))
            .perform(click())
    }

    /**
     * Save income type transactions when all fields provided
     */
    @Test
    fun testIncomeSaveValidateAccountIncomeCategoryAmount_NotNullInput_ReturnSuccess() {
        var transactionType = TransactionTypes.INCOME.name
        val accountModel = AccountsData("Account name", true)
        accountModel.id = 1

        val incomeCatData = IncomeCatData("Income name", true)
        accountModel.id = 1

        edtAccount?.tag = accountModel
        edtCategory?.tag = incomeCatData
        edtAmount?.setText("3002.50")

        onView((withId(R.id.action_done)))
            .perform(click())
    }

    /**
     * Save income type transactions when the account model and income model send as a null value
     */
    @Test
    fun testIncomeSaveValidateAccountIncomeCategoryAmount_NullInput_ReturnMessage() {
        var transactionType = TransactionTypes.INCOME.name
        val accountModel = null
        val incomeCatData = null

        edtAccount?.tag = accountModel
        edtCategory?.tag = incomeCatData
        edtAmount?.setText("")

        onView((withId(R.id.action_done)))
            .perform(click())
    }



    @After
    fun tearDown() {
        mActivity = null
    }
}