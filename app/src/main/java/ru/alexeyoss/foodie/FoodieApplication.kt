package ru.alexeyoss.foodie

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import de.hdodenhof.circleimageview.BuildConfig
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.foodie.core.common.di.AppContextProvider
import ru.alexeyoss.foodie.di.AppComponent
import ru.alexeyoss.foodie.di.AppComponentDepsProvider
import ru.alexeyoss.foodie.scheduling.WorkManagerSettings
import ru.alexeyoss.foodie.scheduling.notifications.NotificationWorker
import ru.alexeyoss.foodie.scheduling.notifications.NotificationWorkerFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FoodieApplication :
    Application(),
    AppContextProvider,
    AppComponentDepsProvider,
    Configuration.Provider {

    override val appComponent: AppComponent by lazy {
        AppComponent.Initializer.init(this@FoodieApplication)
    }

    @Inject
    lateinit var activeActivityHolder: ActiveActivityHolder

    @Inject
    lateinit var notificationWorkerFactory: NotificationWorkerFactory

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@FoodieApplication)
        setDebugLogging()
        performDailyNotificationWork()
    }

    private fun setDebugLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /** Set custom Work Manager factory due passing custom params into Worker constructor */
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(notificationWorkerFactory)
            .build()
    }

    private fun performDailyNotificationWork() {
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            WorkManagerSettings.NotificationDaily.uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            buildNotificationWorkRequest()
        )
    }

    private fun buildNotificationWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<NotificationWorker>(
            dailyNotificationRepeatInterval,
            TimeUnit.HOURS,
            dailyNotificationFlexInterval,
            TimeUnit.HOURS
        ).build()
    }

    companion object {
        const val dailyNotificationRepeatInterval = 12L
        const val dailyNotificationFlexInterval = 12L
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FoodieApplication -> appComponent
        else -> applicationContext.appComponent
    }
