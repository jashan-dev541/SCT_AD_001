
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView

    private var firstNumber = 0.0
    private var currentOperator: Char? = null
    private var startNewNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Number buttons
        val digitIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2,
            R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in digitIds) {
            findViewById<Button>(id).setOnClickListener {
                val digit = (it as Button).text.toString()
                enterDigit(digit)
            }
        }

        // Operator buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            chooseOperator('+')
        }

        findViewById<Button>(R.id.btnSubtract).setOnClickListener {
            chooseOperator('-')
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            chooseOperator('*')
        }

        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            chooseOperator('/')
        }

        // Clear button
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            display.text = "0"
            firstNumber = 0.0
            currentOperator = null
            startNewNumber = true
        }

        // Equals button
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calculateResult()
        }
    }

    private fun enterDigit(digit: String) {
        if (startNewNumber || display.text.toString() == "0") {
            display.text = digit
            startNewNumber = false
        } else {
            display.append(digit)
        }
    }

    private fun chooseOperator(operator: Char) {
        firstNumber = display.text.toString().toDoubleOrNull() ?: 0.0
        currentOperator = operator
        startNewNumber = true
    }

    private fun calculateResult() {
        val secondNumber = display.text.toString().toDoubleOrNull() ?: return
        val operator = currentOperator ?: return

        val result = when (operator) {
            '+' -> firstNumber + secondNumber
            '-' -> firstNumber - secondNumber
            '*' -> firstNumber * secondNumber
            '/' -> {
                if (secondNumber == 0.0) {
                    display.text = "Cannot divide by zero"
                    currentOperator = null
                    startNewNumber = true
                    return
                }
                firstNumber / secondNumber
            }
            else -> return
        }

        display.text = if (result % 1.0 == 0.0) {
            result.toLong().toString()
        } else {
            result.toString()
        }

        firstNumber = result
        currentOperator = null
        startNewNumber = true
    }
}
