package com.example.lab6

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Future

class ActivityExecutionService : AppCompatActivity() {
    companion object {
        const val STATE_SECONDS = "seconds"
    }

    private var seconds = 0
    private lateinit var textViewSeconds: TextView
    private lateinit var executorService: Future<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                seconds = getInt(STATE_SECONDS)
            }
        }
        setContentView(R.layout.activity_main)
        textViewSeconds = findViewById(R.id.textSeconds)
    }

    override fun onStart() {
        Log.d("Activity", "is started")
        executorService = (application as ExecutorApp).executor.submit {
            while (!executorService.isCancelled) {
                Log.d("Thread", "is running for $seconds seconds")
                textViewSeconds.post {
                    textViewSeconds.text = getString(R.string.seconds, seconds++)
                }
                Thread.sleep(1000)
            }
        }
        super.onStart()
    }

    override fun onStop() {
        Log.d("Activity", "is stopped")
        super.onStop()
        executorService.cancel(true)
        if (executorService.isCancelled) Log.d("Thread", "is cancelled")
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