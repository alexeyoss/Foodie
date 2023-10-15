package ru.alexeyoss.foodie

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import de.hdodenhof.circleimageview.BuildConfig
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.foodie.core.common.activity.DefaultActivityLifecycleCallbacks
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

    private val lifecycleCallbacks = object : DefaultActivityLifecycleCallbacks {

        override fun onActivityResumed(activity: Activity) {
            activeActivityHolder.registerActiveActivity(activity as FragmentActivity)
        }

        override fun onActivityPaused(activity: Activity) {
            activeActivityHolder.removeActiveActivity()
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@FoodieApplication)
        setDebugLogging()

        registerActivityLifecycleCallbacks(lifecycleCallbacks)
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
        return PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            dailyNotificationRepeatInterval,
            TimeUnit.MINUTES,
            dailyNotificationFlexInterval,
            TimeUnit.MINUTES
        ).build()
    }
    companion object {
        const val dailyNotificationRepeatInterval = 15L
        const val dailyNotificationFlexInterval = 15L
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FoodieApplication -> appComponent
        else -> applicationContext.appComponent
    }
