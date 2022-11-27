package com.example.lab6

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ExecutorApp: Application() {
    var executor: ExecutorService = Executors.newSingleThreadExecutor()
}