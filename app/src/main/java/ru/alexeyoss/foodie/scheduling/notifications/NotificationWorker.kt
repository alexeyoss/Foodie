package ru.alexeyoss.foodie.scheduling.notifications

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.alexeyoss.foodie.notification.NotificationHelper

class NotificationWorker
@AssistedInject constructor(
    private val context: Context,
    workerParams: WorkerParameters,
    @Assisted private val notificationHelper: NotificationHelper
) : Worker(context, workerParams) {

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        return try {
            notificationHelper.setupNotificationChannels()
            val notification = notificationHelper.createDailyNotification().build()
            // TODO Check permission from PermissionManagerHelper
            NotificationManagerCompat.from(context).notify(1, notification)

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
