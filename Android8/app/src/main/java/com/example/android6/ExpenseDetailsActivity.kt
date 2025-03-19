package com.example.android8

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_detail)

        //pulling data from main activity
        val expenseName = intent.getStringExtra("NAME_OF_EXPENSE")
        val expenseAmount = intent.getDoubleExtra("AMOUNT_OF_EXPENSE", 0.0)
        val expenseDate = intent.getStringExtra("DATE_OF_EXPENSE")

        //binding the pulled data to my textViews
        val nameTextView = findViewById<TextView>(R.id.expenseNameDetails)
        val amountTextView = findViewById<TextView>(R.id.expenseAmountDetails)
        val dateTextView = findViewById<TextView>(R.id.expenseDateDetails)

        nameTextView.text = "Expense Name: $expenseName"

        val formattedAmount = String.format("$%.2f", expenseAmount)
        amountTextView.text = "Cost of Expense: $formattedAmount"

        dateTextView.text = "Date: $expenseDate"
    }
}