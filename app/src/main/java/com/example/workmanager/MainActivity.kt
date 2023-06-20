package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Create a periodic work request
        val notificationWorkRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java, 1, // Repeat every 1 day
            TimeUnit.DAYS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED) // Require unMetered network connection
                .setRequiresCharging(true) // Require the device to be charging
                .build()
        ).build()

        // Enqueue the periodic work request
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "notificationWork",
            ExistingPeriodicWorkPolicy.REPLACE, // Replace existing work with the new one
            notificationWorkRequest
        )
    }
}