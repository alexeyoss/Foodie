package ru.alexeyoss.foodie.notification.di

import dagger.Module
import dagger.Provides
import ru.alexeyoss.foodie.core.common.di.AppContextProvider
import ru.alexeyoss.foodie.notification.NotificationHelper
import ru.alexeyoss.foodie.notification.NotificationHelperImpl

@Module
interface NotificationModule {
    companion object {
        @Provides
        fun provideNotificationHelper(appContextProvider: AppContextProvider): NotificationHelper {
            return NotificationHelperImpl(appContextProvider.getApplicationContext())
        }
    }
}
