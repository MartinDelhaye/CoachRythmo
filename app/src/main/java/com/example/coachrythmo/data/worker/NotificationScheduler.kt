package com.example.coachrythmo.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationScheduler {

    fun scheduleRoutineNotification(
        context: Context,
        routineName: String,
        routineTime: String,
        dayOfWeek: Int
    ) {
        val delayMinutes = calculateDelayMinutes(routineTime, dayOfWeek)

        val data = workDataOf(
            "routine_name" to routineName,
            "routine_time" to routineTime
        )

        val request = PeriodicWorkRequestBuilder<NotificationWorker>(7L, TimeUnit.DAYS)
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "notification_$routineName",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancelRoutineNotification(context: Context, routineName: String) {
        WorkManager.getInstance(context).cancelUniqueWork("notification_$routineName")
    }

    private fun calculateDelayMinutes(routineTime: String, dayOfWeek: Int): Long {
        val parts = routineTime.split(":")
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: 8
        val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0

        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, dayOfWeek)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute - 15)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.before(now)) {
            target.add(Calendar.WEEK_OF_YEAR, 1)
        }

        val diffMs = target.timeInMillis - now.timeInMillis
        return maxOf(1L, diffMs / 60000)
    }
}