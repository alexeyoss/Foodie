package ru.alexeyoss.services.preference.di

import dagger.Component
import dagger.Module
import ru.alexeyoss.core.common.di.MainToolsProvider
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.services.preference.prefs.PermissionsPrefs


interface PreferenceProvider {
    fun providePermissionPreference(): PermissionsPrefs
}

@[PerApplication Component(
    modules = [PreferenceModule::class],
    dependencies = [MainToolsProvider::class]
)]
interface PreferenceComponent : PreferenceProvider

@Module
internal class PreferenceModule

