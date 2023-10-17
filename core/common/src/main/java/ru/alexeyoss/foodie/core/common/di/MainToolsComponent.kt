package ru.alexeyoss.foodie.core.common.di

import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.core.common.di.modules.MainToolsModule
import ru.alexeyoss.foodie.core.common.di.scope.PerApplication

interface MainToolsProvider {
    fun provideContext(): AppContextProvider

    @CoroutinesModule.IoDispatcher
    fun provideIODispatcher(): CoroutineDispatcher

    @CoroutinesModule.MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher

    @CoroutinesModule.DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher
}

@[
PerApplication Component(
    modules = [
        MainToolsModule::class,
        CoroutinesModule::class
    ]
)
]
interface MainToolsComponent : MainToolsProvider {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(appContextProvider: AppContextProvider): Builder

        fun build(): MainToolsComponent
    }

    class Initializer private constructor() {
        companion object {
            fun init(appContextProvider: AppContextProvider): MainToolsProvider = DaggerMainToolsComponent.builder()
                .app(appContextProvider)
                .build()
        }
    }
}
