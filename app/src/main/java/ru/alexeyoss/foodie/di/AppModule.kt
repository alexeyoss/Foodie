package ru.alexeyoss.foodie.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.foodie.FoodieApplication
import ru.alexeyoss.foodie.redux.ApplicationState
import ru.alexeyoss.foodie.redux.Store
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext application: Context): FoodieApplication =
        application as FoodieApplication

    @Singleton
    @Provides
    fun provideApplicationStateStore(): Store<ApplicationState> = Store(ApplicationState())
}