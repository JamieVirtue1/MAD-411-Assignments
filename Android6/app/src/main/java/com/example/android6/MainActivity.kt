package com.example.android6

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val usersNameEditText: EditText = findViewById(R.id.users_name)
        val showNameButton: Button = findViewById(R.id.show_name_button)
        val viewNameTextView: TextView = findViewById(R.id.view_name)

        showNameButton.setOnClickListener{
            val formattedName = usersNameEditText.text.toString()

            viewNameTextView.text = "Hello, $formattedName!"
        }
    }
}