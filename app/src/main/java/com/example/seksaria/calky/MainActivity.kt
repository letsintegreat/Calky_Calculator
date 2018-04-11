package com.example.seksaria.calky

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.HorizontalScrollView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var operationRecentlyDone = false

    // Variable to check whether or not any operator is pressed!
    private var firstOperatorPressed = false
    private var secondOperatorPressed = false
    private var equalButtonPressed = false

    // Variables to store first and second operand
    private var firstOperand = 0
    private var secondOperand = 0

    // Variable to store current operator
    private var currentOperator = '+'

    // Variable to check whether or not any IllegalArgumentException is found!
    private var exceptionFound = false

    private var calculationHistory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_1.setOnClickListener(View.OnClickListener { showOnScreen("1") })

        button_2.setOnClickListener(View.OnClickListener { showOnScreen("2") })

        button_3.setOnClickListener(View.OnClickListener { showOnScreen("3") })

        button_4.setOnClickListener(View.OnClickListener { showOnScreen("4") })

        button_5.setOnClickListener(View.OnClickListener { showOnScreen("5") })

        button_6.setOnClickListener(View.OnClickListener { showOnScreen("6") })

        button_7.setOnClickListener(View.OnClickListener { showOnScreen("7") })

        button_8.setOnClickListener(View.OnClickListener { showOnScreen("8") })

        button_9.setOnClickListener(View.OnClickListener { showOnScreen("9") })

        button_0.setOnClickListener(View.OnClickListener { showOnScreen("0") })

        button_addition.setOnClickListener(View.OnClickListener { doOperation('+') })

        button_subtract.setOnClickListener(View.OnClickListener { doOperation('-') })

        button_multi.setOnClickListener(View.OnClickListener { doOperation('*') })

        button_divide.setOnClickListener(View.OnClickListener { doOperation('/') })

        button_equal.setOnClickListener(View.OnClickListener {
            if (exceptionFound or equalButtonPressed) {
                clearEverything()
            } else if (firstOperatorPressed) {
                secondOperatorPressed = true
                if (screen_text_view.text.toString().equals("")) {
                    secondOperand = 0
                } else {
                    secondOperand = screen_text_view.text.toString().toInt()
                }
                when (currentOperator) {
                    '+' -> {
                        screen_text_view.text = (firstOperand + secondOperand).toString()
                        firstOperand += secondOperand
                    }
                    '-' -> {
                        screen_text_view.text = (firstOperand - secondOperand).toString()
                        firstOperand -= secondOperand
                    }
                    '*' -> {
                        screen_text_view.text = (firstOperand * secondOperand).toString()
                        firstOperand *= secondOperand
                    }
                    '/' -> {
                        try {
                            if (secondOperand == 0) {
                                throw IllegalArgumentException()
                            } else {
                                screen_text_view.text = (firstOperand / secondOperand).toString()
                            }
                        } catch (e : IllegalArgumentException) {
                            screen_text_view.text = "Can't Divide By Zero, Press Anything"
                            screen_text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
                            exceptionFound = true
                        }
                    }
                }
                operationRecentlyDone = true
                calculationHistory += "$secondOperand "
                history_text_view.text = calculationHistory
                scroller.post(Runnable { scroller.fullScroll(HorizontalScrollView.FOCUS_RIGHT) })
                equalButtonPressed = true
            }
        })

        button_clear.setOnClickListener(View.OnClickListener { clearEverything() })

    }

    private fun showOnScreen(sNumber : String) {

        if (exceptionFound or equalButtonPressed) {
            clearEverything()
        } else if (operationRecentlyDone) {
            screen_text_view.text = sNumber
        } else {
            screen_text_view.append(sNumber)
        }
        operationRecentlyDone = false
    }

    private fun doOperation(operation : Char) {
        if (exceptionFound or equalButtonPressed) {
            clearEverything()
        } else if (!firstOperatorPressed) {
            if (screen_text_view.text.toString().equals("")) {
                firstOperand = 0
            } else {
                firstOperand = screen_text_view.text.toString().toInt()
            }
            screen_text_view.text = ""
            firstOperatorPressed = true
            currentOperator = operation
            calculationHistory += "$firstOperand $currentOperator "
            history_text_view.text = calculationHistory
            scroller.post(Runnable { scroller.fullScroll(HorizontalScrollView.FOCUS_RIGHT) })
        } else {
            secondOperatorPressed = true
            if (screen_text_view.text.toString().equals("")) {
                secondOperand = 0
            } else {
                secondOperand = screen_text_view.text.toString().toInt()
            }
            when (currentOperator) {
                '+' -> {
                    screen_text_view.text = (firstOperand + secondOperand).toString()
                    firstOperand += secondOperand
                }
                '-' -> {
                    screen_text_view.text = (firstOperand - secondOperand).toString()
                    firstOperand -= secondOperand
                }
                '*' -> {
                    screen_text_view.text = (firstOperand * secondOperand).toString()
                    firstOperand *= secondOperand
                }
                '/' -> {
                    try {
                        if (secondOperand == 0) {
                            throw IllegalArgumentException()
                        } else {
                            screen_text_view.text = (firstOperand / secondOperand).toString()
                        }
                    } catch (e : IllegalArgumentException) {
                        screen_text_view.text = "Can't Divide By Zero, Press Anything"
                        screen_text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
                        exceptionFound = true
                    }
                }
            }
            operationRecentlyDone = true
            currentOperator = operation
            calculationHistory += "$secondOperand $currentOperator "
            history_text_view.text = calculationHistory
            scroller.post(Runnable { scroller.fullScroll(HorizontalScrollView.FOCUS_RIGHT) })
        }
    }

    private fun clearEverything() {
        screen_text_view.text = ""
        history_text_view.text = ""
        firstOperand = 0
        secondOperand = 0
        firstOperatorPressed = false
        secondOperatorPressed = false
        operationRecentlyDone = false
        exceptionFound = false
        screen_text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48f)
        calculationHistory = ""
        equalButtonPressed = false
    }
}
