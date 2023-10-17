package ru.alexeyoss.foodie.scheduling.notifications

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
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

    override fun doWork(): Result {
        return try {
            notificationHelper.setupNotificationChannels()
            val notification = notificationHelper.getDailyNotificationBuilder().build()

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO save info to localStore OR analytical service
                Unit
            } else {
                NotificationManagerCompat.from(context).notify(1, notification)
            }

            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }
}
