package ru.alexeyoss.foodie.core.common.di

import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.core.common.di.modules.MainToolsModule
import ru.alexeyoss.foodie.core.common.di.scope.PerApplication

interface MainToolsProvider {
    fun provideContext(): App
    fun provideActiveActivityHolder(): ActiveActivityHolder

    @CoroutinesModule.IoDispatcher
    fun provideIODispatcher(): CoroutineDispatcher

    @CoroutinesModule.MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher

    @CoroutinesModule.DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher
}

@[PerApplication Component(
    modules = [
        MainToolsModule::class,
        CoroutinesModule::class
    ]
)]
interface MainToolsComponent : MainToolsProvider {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: App): Builder

        fun build(): MainToolsComponent
    }

    class Initializer private constructor() {
        companion object {
            fun init(app: App): MainToolsProvider = DaggerMainToolsComponent.builder()
                .app(app)
                .build()
        }
    }
}