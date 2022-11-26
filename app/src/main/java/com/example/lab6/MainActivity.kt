package com.example.lab6

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var seconds = 0
    private lateinit var textViewSeconds: TextView
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewSeconds = findViewById(R.id.textSeconds)
    }

    override fun onPause() {
        Log.d("Pause", "is called")
        Log.d("App", "is collapsed")
        stopTimer()
        super.onPause()
    }

    override fun onResume() {
        Log.d("Resume", "is called")
        Log.d("App", "is opened")
        runTimer()
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(getString(R.string.seconds), seconds)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        seconds = savedInstanceState.getInt(getString(R.string.seconds))
        super.onRestoreInstanceState(savedInstanceState)
    }

    private val backGroundThread = object : Runnable {
        override fun run() {
            textViewSeconds.text = getString(R.string.seconds, seconds++)
            handler.postDelayed(this, 1000)
        }
    }

    private fun runTimer() {
        Log.d("Thread", "is run")
        handler = Handler(Looper.getMainLooper())
        backGroundThread.run()
    }

    private fun stopTimer() {
        Log.d("Thread", "is stopped")
        handler.removeCallbacks(backGroundThread)
    }

}