package com.example.coachrythmo.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coachrythmo.R

class NotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val routineName = inputData.getString("routine_name") ?: "Ta séance"
        val routineTime = inputData.getString("routine_time") ?: ""

        createNotificationChannel()
        showNotification(routineName, routineTime)

        return Result.success()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Rappels de séance",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications pour tes routines d'entraînement"
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun showNotification(routineName: String, routineTime: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_home)
            .setContentTitle("💪 Séance bientôt !")
            .setContentText("$routineName commence à $routineTime")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(routineName.hashCode(), notification)
    }

    companion object {
        const val CHANNEL_ID = "routine_notifications"
    }
}