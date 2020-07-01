package com.inex.expensetracker.utils

class PrePopulateData {
    companion object {
        //Account types
        const val ACC_CASH = "Cash"
        const val ACC_CREDIT_CARD = "Credit Card"
        const val ACC_BANK_ACCOUNT = "Bank account"

        //Expenses category
        const val EXP_TAX = "Tax"
        const val EXP_GROCERY = "Grocery"
        const val EXP_ENTERTAINMENT = "Entertainment"
        const val EXP_GYM = "Gym"
        const val EXP_HEALTH = "Health"

        //Income category
        const val INCOME_SALARY = "Salary"
        const val INCOME_DIVIDENDS = "Dividends"

        //Transaction types
        const val INCOME = true
        const val EXPENSE = false
    }
}