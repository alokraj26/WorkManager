package com.example.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context,parameters: WorkerParameters) : Worker(context,parameters) {
    override fun doWork(): Result {
        showNotification("Hello from WorkManger")
        return Result.success()
    }

    private fun showNotification(message: String) {
        val channelId = "my_channel_id"
        val channelName = "My Channel"

        // Create a notification channel (required for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create a unique notificationId using the current time
        val notificationId = System.currentTimeMillis().toInt()

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("My App")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notifications)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show the notification
        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompat.notify(notificationId, notificationBuilder.build())
    }

}