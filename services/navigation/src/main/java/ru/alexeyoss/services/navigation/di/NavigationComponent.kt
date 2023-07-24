package ru.alexeyoss.services.navigation.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.alexeyoss.core.common.di.scope.PerApplication

interface NavigationProvider {
    fun provideMainRouter(): Router
    fun provideNavigatorHolder(): NavigatorHolder

}

@[PerApplication Component(
    modules = [NavigationModule::class]
)]
interface NavigationComponent : NavigationProvider {
    class Initializer private constructor() {
        companion object {
            fun init(): NavigationProvider = DaggerNavigationComponent.builder().build()
        }
    }
}

@Module
internal object NavigationModule {
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


