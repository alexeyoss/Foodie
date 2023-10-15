package ru.alexeyoss.foodie.notification

import androidx.core.app.NotificationCompat

interface NotificationHelper {
    fun setupNotificationChannels()
    fun createDailyNotification(): NotificationCompat.Builder
}
