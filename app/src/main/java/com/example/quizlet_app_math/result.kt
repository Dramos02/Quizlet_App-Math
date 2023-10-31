package com.example.quizlet_app_math

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizlet_app_math.databinding.ActivityResultBinding

class result : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIdPlay.setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
            finish()
        }

        binding.btnIdExit.setOnClickListener {
            finish()
        }

        val intentResult = intent
        score = intentResult.getIntExtra("score", 0)
        binding.txtviewIdResult.text = score.toString()
    }
}