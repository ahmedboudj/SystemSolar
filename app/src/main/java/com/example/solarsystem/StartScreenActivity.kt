package com.example.solarsystem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class StartScreenActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val playButton: Button = findViewById(R.id.play_button)
        playButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
