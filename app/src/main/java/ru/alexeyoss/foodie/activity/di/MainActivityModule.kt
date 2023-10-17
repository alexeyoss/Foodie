package ru.alexeyoss.foodie.activity.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import ru.alexeyoss.foodie.activity.MainActivityViewModel
import ru.alexeyoss.foodie.activity.domain.repositories.LocationRepository
import ru.alexeyoss.foodie.activity.repositories.AdapterLocationRepository
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.core.common.di.scope.PerApplication
import ru.alexeyoss.foodie.permissions.PermissionManager
import ru.alexeyoss.foodie.permissions.PermissionManagerImpl

@Module
interface MainActivityModule {

    @Binds
    fun bindLocationRepository(impl: AdapterLocationRepository): LocationRepository

    @Binds
    @[IntoMap ClassKey(MainActivityViewModel::class)]
    fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel
    companion object {
        @PerApplication
        @Provides
        fun provideActiveActivityHolder(): ActiveActivityHolder = ActiveActivityHolder()

        @PerApplication
        @Provides
        fun providePermissionManager(
            activeActivityHolder: ActiveActivityHolder,
            @CoroutinesModule.DefaultDispatcher defaultDispatcher: CoroutineDispatcher
        ): PermissionManager = PermissionManagerImpl(activeActivityHolder, defaultDispatcher)
    }
}
