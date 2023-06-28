package ru.alexeyoss.foodie.app

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.core.common.ActiveActivityHolder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    internal fun provideApplication(@ApplicationContext application: Context): FoodieApp =
        application as FoodieApp

    @Singleton
    @Provides
    internal fun provideActiveActivityHolder(): ActiveActivityHolder = ActiveActivityHolder()
}