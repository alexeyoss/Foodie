package ru.alexeyoss.foodie.services.preference.di

import dagger.Component
import ru.alexeyoss.foodie.core.common.di.MainToolsProvider
import ru.alexeyoss.foodie.core.common.di.scope.PerApplication
import ru.alexeyoss.foodie.services.preference.prefs.PermissionsPrefs


interface PreferenceProvider {
    fun providePermissionPreference(): PermissionsPrefs
}

@[PerApplication Component(
    dependencies = [MainToolsProvider::class]
)]
interface PreferenceComponent : PreferenceProvider {

    @Component.Builder
    interface Builder {
        fun deps(mainToolsComponent: MainToolsComponent): Builder
        fun build(): PreferenceComponent
    }
}

