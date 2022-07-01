package com.example.quizapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        val buttonStart: Button = findViewById(R.id.btn_start)
        val etName: AppCompatEditText = findViewById(R.id.et_name)
        buttonStart.setOnClickListener {
            buttonStartTapped(etName)
        }
    }

    private fun buttonStartTapped(etName: AppCompatEditText) {
        if (etName.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter your name!", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            startActivity(intent)
        }
    }
}