package ru.alexeyoss.foodie.services.navigation.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides

@Module
object NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    internal fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    internal fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}


