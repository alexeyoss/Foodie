package ru.alexeyoss.foodie.domain

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.foodie.FoodieApplication

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideApplication(@ApplicationContext application: Context): FoodieApplication =
        application as FoodieApplication
}