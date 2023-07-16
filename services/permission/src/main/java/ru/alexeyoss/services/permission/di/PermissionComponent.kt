package ru.alexeyoss.services.permission.di

import dagger.Component
import ru.alexeyoss.core.common.di.CoroutinesModule
import ru.alexeyoss.core.common.di.MainToolsProvider
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.services.permission.PermissionManager
import ru.alexeyoss.services.preference.di.DaggerPreferenceComponent
import ru.alexeyoss.services.preference.di.PreferenceProvider


interface PermissionProvider {
    fun providePermissionManager(): PermissionManager
}

@[PerApplication Component(
    modules = [CoroutinesModule::class],
    dependencies = [
        MainToolsProvider::class,
        PreferenceProvider::class
    ]
)]
interface PermissionComponent : PermissionProvider {

    class Initializer private constructor() {
        companion object {

            fun init(mainToolsProvider: MainToolsProvider): PermissionProvider {
                val preferenceComponent = DaggerPreferenceComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .build()

                return DaggerPermissionComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .preferenceProvider(preferenceComponent)
                    .build()
            }
        }
    }
}
