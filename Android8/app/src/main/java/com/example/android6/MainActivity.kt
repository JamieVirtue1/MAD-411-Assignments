package com.example.android8

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date

class MainActivity : AppCompatActivity() {

    lateinit var expenseName: EditText
    lateinit var expenseAmount: EditText
    lateinit var expenseDate: DatePicker
    lateinit var expenseRecyclerView: RecyclerView
    lateinit var addExpenseButton: Button
    lateinit var financialTipsButton: Button
    lateinit var recyclerViewAdapter: ExpenseAdapter
    val listOfExpenses = mutableListOf<Pair<String, Pair<String, Double>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //logging onCreate message
        Log.d("ActivityLifecycle", "onCreate() has been called")

        expenseName = findViewById(R.id.expenseName)
        expenseAmount = findViewById(R.id.expenseAmount)
        expenseDate = findViewById(R.id.expenseDate)

        //recycler view initialization and linear layout application
        expenseRecyclerView = findViewById(R.id.expenseRecyclerView)
        expenseRecyclerView.layoutManager = LinearLayoutManager(this)

        addExpenseButton = findViewById(R.id.addExpenseButton)

        financialTipsButton = findViewById(R.id.financialTipsButton)


        //setting the adapter to the recycler view (not sure why i cant simplify this)
        recyclerViewAdapter = ExpenseAdapter(listOfExpenses)
        expenseRecyclerView.adapter = recyclerViewAdapter

        //add expense button and logic
        addExpenseButton.setOnClickListener {
            val name = expenseName.text.toString()
            val cost = expenseAmount.text.toString()

            val day = expenseDate.dayOfMonth
            val month = expenseDate.month + 1
            val year = expenseDate.year

            val date = "$day/$month/$year"

            //checking to make sure variables arent empty
            if (name.isNotEmpty() && cost.isNotEmpty()) {

                //adding expense to list that takes pair of string and double and a solo string
                val expense = Pair(name, Pair(date, cost.toDouble()))
                listOfExpenses.add(expense)
                //notifying the adapter a change has been made at this specific index
                recyclerViewAdapter.notifyItemInserted(listOfExpenses.size - 1)

                //clear the EditText fields for new inputs
                expenseName.text.clear()
                expenseAmount.text.clear()

            }
        }

        //button that sends to financial help
        financialTipsButton.setOnClickListener {
            val url = "https://www.cibc.com/en/business/advice-centre/articles/financial-tips.html"

            val financialTipsIntent = Intent(Intent.ACTION_VIEW)
            financialTipsIntent.data = Uri.parse(url)

            startActivity(financialTipsIntent)
        }
    }


    override fun onStart(){
        super.onStart()
        //logging onCreate message
        Log.d("ActivityLifecycle", "onStart() has been called")

    }

    override fun onResume(){
        super.onResume()
        //logging onResume message
        Log.d("ActivityLifecycle", "onResume() has been called")

    }

    override fun onPause(){
        super.onPause()
        //logging onCreate message
        Log.d("ActivityLifecycle", "onPause() has been called")

    }

    override fun onStop(){
        super.onStop()
        //logging onCreate message
        Log.d("ActivityLifecycle", "onStop() has been called")

    }

    override fun onDestroy(){
        super.onDestroy()
        //logging onCreate message
        Log.d("ActivityLifecycle", "onDestroy() has been called")

    }
}

//adapter class for the RecyclerView
class ExpenseAdapter(
    private val expenses: MutableList<Pair<String, Pair<String, Double>>>,
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    //onCreateViewHolder function that runs when view created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {

        //making recyclerview follow a horizontal linear layout
        val layout = LinearLayout(parent.context)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.setPadding(16,16,16,16)

        //textview for the expense name field
        val nameTextView = TextView(parent.context).apply {
            //styling parameters
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                //making space between recyclerview fields
                setMargins(0,0,20,0)
            }
            textSize = 16f
        }

        //textview for the expense amount field
        val amountTextView = TextView(parent.context).apply {
            //styling parameters
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                //making space between recyclerview fields
                setMargins(0,0,20,0)
            }
            textSize = 16f
        }

        //textview for the date field
        val dateTextView = TextView(parent.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0,20,0,20)
            }
        }

        //button for deleting the expense from the recycler view
        val deleteButton = Button(parent.context).apply {
            text = "Delete"
            //styling parameters
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        //button for showing item details
        val showDetailsButton = Button(parent.context).apply{
            text = "Show Details"

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }


        //adding the recyclerview fields to the layout
        layout.addView(nameTextView)
        layout.addView(amountTextView)
        layout.addView(deleteButton)
        layout.addView(showDetailsButton)

        //returning the recyclerview with the created layout
        return ExpenseViewHolder(layout, nameTextView, amountTextView, dateTextView, deleteButton, showDetailsButton)
    }

    //binding my data to my view holder
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        //setting individual expenses based on their position in the expenses array
        val expense = expenses[position]

        val name = expense.first
        val date = expense.second.first
        val amount = expense.second.second

        //setting my first pair value to name
        holder.nameTextView.text = name
        //setting my second pair value to cost amount
        holder.amountTextView.text = "$$amount"

        holder.dateTextView.text = date


        //set the delete button listener
        holder.deleteButton.setOnClickListener {

            expenses.removeAt(position)
            notifyItemRemoved(position)
        }

        //button that swaps screen to ExpenseDetailsActivity
        holder.showDetailsButton.setOnClickListener{

            val passedInformation = holder.itemView.context
            val sendDataIntent = Intent(passedInformation, ExpenseDetailsActivity::class.java).apply {
                //passing data using intent extras
                putExtra("NAME_OF_EXPENSE", expense.first)
                putExtra("AMOUNT_OF_EXPENSE", expense.second.second)
                putExtra("DATE_OF_EXPENSE", expense.second.first)
            }
            passedInformation.startActivity(sendDataIntent)
        }
    }

    //forced me to add this. Counts pairs of items in the expenses list
    override fun getItemCount(): Int = expenses.size

    //viewHolder to hold the views for each expense item
    class ExpenseViewHolder(
        itemView: View,
        val nameTextView: TextView,
        val amountTextView: TextView,
        val dateTextView: TextView,
        val deleteButton: Button,
        val showDetailsButton: Button
    ) : RecyclerView.ViewHolder(itemView)
}
