package com.example.solarsystem

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

    private lateinit var alienSolarSystem: AlienSolarSystem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alienSolarSystem = AlienSolarSystem(this)
        setContentView(alienSolarSystem)
    }

    override fun onResume() {
        super.onResume()
        alienSolarSystem.resume()
    }

    override fun onPause() {
        super.onPause()
        alienSolarSystem.pause()
    }
}
