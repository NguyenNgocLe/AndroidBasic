package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var btnOne: Button? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
        btnOne = findViewById(R.id.btnOne)
        btnOne?.setOnClickListener {
            tvInput?.append("1")
        }
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric&& !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
}