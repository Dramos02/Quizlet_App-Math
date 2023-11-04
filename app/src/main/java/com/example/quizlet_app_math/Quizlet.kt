package com.example.quizlet_app_math

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.quizlet_app_math.databinding.ActivityQuizletBinding

class Quizlet : AppCompatActivity() {
    private lateinit var binding: ActivityQuizletBinding

    private val randomnum = java.util.Random()
    private var num1 = 0
    private var num2 = 0
    private var userAnswer = 0
    private var correctAnswer = 0
    private var userScore = 0
    private var userLife = 3

    private lateinit var timer: CountDownTimer
    private val START_TIMER_IN_MILLIS: Long = 31000
    private var timerRunning = false
    private var timeLeftInMillis = START_TIMER_IN_MILLIS

    private var okButtonClicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtIdScore.text = userScore.toString()
        binding.txtIdLife.text = userLife.toString()
        binding.txtIdTime.text = (timeLeftInMillis / 1000).toInt().toString()

        gameContinue()

        binding.btnIdOk.setOnClickListener {
            if (!okButtonClicked) {
                val userAnswerText = binding.txtIdAnswer.text.toString().trim()

                if (userAnswerText.isEmpty()) {
                    displayMessage("Please enter your answer Senpai.")
                } else {
                    userAnswer = userAnswerText.toInt()
                    pauseTimer()

                    if (userAnswer == correctAnswer) {
                        userScore++
                        binding.txtIdScore.text = userScore.toString()
                        binding.txtIdQuestion.text = "You Are Correct"
                        displayMessage("You Are Correct")
                    } else {
                        userLife--
                        binding.txtIdLife.text = userLife.toString()
                        binding.txtIdQuestion.text = "You Are Wrong"
                        displayMessage("You Are Wrong")
                    }

                    okButtonClicked = true
                }
            } else {
                displayMessage("You have already answered.")
            }
        }

        binding.btnIdNext.setOnClickListener {
            val userAnswerText = binding.txtIdAnswer.text.toString().trim()
            if (userAnswerText.isEmpty()) {
                displayMessage("Don't click it Senpai answer it first.")
            } else {
                binding.txtIdAnswer.text.clear()
                resetTimer()
                okButtonClicked = false

                if (userLife <= 0) {
                    Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT).show()
                    val intentResult = Intent(this, result::class.java)
                    intentResult.putExtra("score", userScore)
                    startActivity(intentResult)
                    finish()
                } else {
                    gameContinue()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun gameContinue() {
        num1 = randomnum.nextInt(100)
        num2 = randomnum.nextInt(100)
        val operation = intent.getStringExtra("operation")

        if (operation == "subtraction") {
            num1 = randomnum.nextInt(100)
            num2 = randomnum.nextInt(num1 + 1)
        }

        val operatorSymbol = when (operation) {
            "addition" -> "+"
            "subtraction" -> "-"
            "multiplication" -> "*"
            else -> ""
        }

        correctAnswer = when (operation) {
            "addition" -> num1 + num2
            "subtraction" -> num1 - num2
            "multiplication" -> num1 * num2
            else -> 0
        }

        binding.txtIdQuestion.text = "$num1 $operatorSymbol $num2"
        binding.txtIdAnswer.text.clear()
        startTimer()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timerRunning = false
                pauseTimer()
                resetTimer()
                userLife--
                binding.txtIdLife.text = userLife.toString()

                if (userLife > 0) {
                    gameContinue()
                } else {
                    handleGameOver()

                }
            }
        }.start()

        timerRunning = true
    }

    private fun pauseTimer() {
        timer.cancel()
        timerRunning = false
    }

    private fun resetTimer() {
        timeLeftInMillis = START_TIMER_IN_MILLIS
        updateText()
    }

    private fun updateText() {
        val seconds = (timeLeftInMillis / 1000).toInt() % 60
        val timeLeft = String.format("%02d", seconds)
        binding.txtIdTime.text = timeLeft
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    @SuppressLint("SetTextI18n")
    private fun handleGameOver() {
        binding.txtIdQuestion.text = "Game Over"
        binding.btnIdOk.isEnabled = false
        binding.btnIdNext.isEnabled = false

        Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT).show()

        val intentResult = Intent(this, result::class.java)
        intentResult.putExtra("score", userScore)
        startActivity(intentResult)
        finish()
    }
}
