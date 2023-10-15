package ru.alexeyoss.foodie.scheduling.notifications

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.alexeyoss.foodie.notification.NotificationHelper
import javax.inject.Inject

/**
 * Factory for [NotificationWorker]
 * @see WorkerFactory
 * */
class NotificationWorkerFactory
@Inject constructor(
    private val notificationHelper: NotificationHelper
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = NotificationWorker(appContext, workerParameters, notificationHelper)
}
