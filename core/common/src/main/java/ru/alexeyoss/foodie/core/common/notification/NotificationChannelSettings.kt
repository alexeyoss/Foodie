package ru.alexeyoss.foodie.core.common.notification

import android.app.Notification
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import ru.alexeyoss.foodie.core.common.R

enum class NotificationChannelSettings(
    val id: String,
    val notifName: String,
    @StringRes val desc: Int,
    val enableVibration: Boolean,
    @IntegerRes val lockScreenVisibility: Int,
) {
    DAILY(
        "DAILY",
        "Daily notification",
        R.string.dailyNotificationChannelDesc,
        true,
        Notification.VISIBILITY_PUBLIC
    ),
}

enum class NotificationSettings(
    @StringRes val contentTitle: Int,
    @StringRes val contentText: Int
) {
    DAILY(R.string.dailyNotificationContentTitle, R.string.dailyNotificationContentText),
}
