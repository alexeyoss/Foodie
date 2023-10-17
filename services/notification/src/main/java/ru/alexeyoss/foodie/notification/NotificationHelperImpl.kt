package ru.alexeyoss.foodie.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.alexeyoss.foodie.core.common.notification.NotificationChannelSettings
import ru.alexeyoss.foodie.core.common.notification.NotificationSettings
import ru.alexeyoss.foodie.core_ui.theme.R.drawable as CoreDrawable

class NotificationHelperImpl
constructor(
    private val context: Context
) : NotificationHelper {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun setupNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dailyChannel = NotificationChannel(
                NotificationChannelSettings.DAILY.id,
                NotificationChannelSettings.DAILY.notifName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(NotificationChannelSettings.DAILY.desc)
                enableLights(NotificationChannelSettings.DAILY.enableVibration)
                lockscreenVisibility = NotificationChannelSettings.DAILY.lockScreenVisibility
            }
            notificationManager.createNotificationChannel(dailyChannel)
        }
    }

    override fun getDailyNotificationBuilder(): NotificationCompat.Builder {
        // TODO experiment with the pending intent
        return NotificationCompat.Builder(context, NotificationChannelSettings.DAILY.id)
            .setSmallIcon(CoreDrawable.ic_notification)
            .setContentTitle(context.getString(NotificationSettings.DAILY.contentTitle))
            .setContentText(context.getString(NotificationSettings.DAILY.contentText))
    }
}
