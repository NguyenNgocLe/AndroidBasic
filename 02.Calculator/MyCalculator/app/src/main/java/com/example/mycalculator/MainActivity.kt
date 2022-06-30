package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

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

    fun onClear() {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint() {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("+") -> {
                        val splitedValue = tvValue.split("+")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        tvInput?.text =
                            removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                    //
                    tvValue.contains("-") -> {
                        val splitedValue = tvValue.split("-")
                        var one = splitedValue[0]
                        var two = splitedValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput?.text =
                            removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    //
                    tvValue.contains("*") -> {
                        val splitedValue = tvValue.split("*")
                        var one = splitedValue[0]
                        var two = splitedValue[1]
                        if(prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    //
                    tvValue.contains("/") -> {
                        val splitedValue = tvValue.split("/")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Remove the zero after decimal point
     */
    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    fun onOperator(view: View) {
        tvInput?.text.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false // Update the flag
                lastDot = false // Update the flag
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }
}