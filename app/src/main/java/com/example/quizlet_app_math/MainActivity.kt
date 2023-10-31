package com.example.quizlet_app_math

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizlet_app_math.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIdAdd.setOnClickListener {
            startQuizletActivity("addition")
        }

        binding.btnIdSubtract.setOnClickListener {
            startQuizletActivity("subtraction")
        }

        binding.btnIdMultiplication.setOnClickListener {
            startQuizletActivity("multiplication")
        }
    }

    private fun startQuizletActivity(operation: String) {
        val intentQuizlet = Intent(this, Quizlet::class.java)
        intentQuizlet.putExtra("operation", operation)
        startActivity(intentQuizlet)
        finish()
    }
}