package ru.alexeyoss.foodie.notification

import androidx.core.app.NotificationCompat

interface NotificationHelper {
    fun setupNotificationChannels()
    fun getDailyNotificationBuilder(): NotificationCompat.Builder
}
