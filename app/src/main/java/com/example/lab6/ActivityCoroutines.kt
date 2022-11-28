package com.example.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ActivityCoroutines : AppCompatActivity() {
    companion object {
        const val STATE_SECONDS = "seconds"
    }

    private var seconds = 0
    private lateinit var textViewSeconds: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                seconds = getInt(STATE_SECONDS)
            }
        }

        setContentView(R.layout.activity_main)
        textViewSeconds = findViewById(R.id.textSeconds)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) {
                    Log.d("Thread", "is running for $seconds seconds")
                    textViewSeconds.text = getString(R.string.seconds, seconds++)
                    delay(1000)
                }
            }
        }
    }

    override fun onStart() {
        Log.d("Activity", "is started")
        super.onStart()
    }

    override fun onStop() {
        Log.d("Activity", "is stopped")
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
}