package ru.alexeyoss.services.preference.di

import dagger.Component
import ru.alexeyoss.core.common.di.MainToolsComponent
import ru.alexeyoss.core.common.di.MainToolsProvider
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.services.preference.prefs.PermissionsPrefs


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

