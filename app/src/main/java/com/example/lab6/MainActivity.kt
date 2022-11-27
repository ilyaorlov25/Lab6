package com.example.lab6

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val STATE_SECONDS = "seconds"
    }

    private var seconds = 0
    private lateinit var textViewSeconds: TextView
    private lateinit var backgroundThread: Thread
    @Volatile private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                seconds = getInt(STATE_SECONDS)
            }
        }
        setContentView(R.layout.activity_main)
        textViewSeconds = findViewById(R.id.textSeconds)
        Log.d("Activity", "is created")
    }

    override fun onStart() {
        Log.d("Activity", "is started")
        if (!isRunning) {
            isRunning = true
            backgroundThread = Thread(runnable)
            backgroundThread.start()
        }
        super.onStart()
    }

    override fun onStop() {
        Log.d("Activity", "is stopped")
        isRunning = false
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SECONDS, seconds)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.getInt(STATE_SECONDS)
        super.onRestoreInstanceState(savedInstanceState)
    }

    private val runnable = Runnable {
        while (isRunning) {
            Log.d("Thread", "is running for $seconds seconds")
            textViewSeconds.post {
                textViewSeconds.text = getString(R.string.seconds, seconds++)
            }
            Thread.sleep(1000)
        }
        Log.d("Thread", "is stopped")
    }
}