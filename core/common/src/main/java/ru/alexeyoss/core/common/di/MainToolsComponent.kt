package ru.alexeyoss.core.common.di

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.alexeyoss.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.core.common.di.scope.PerApplication

interface MainToolsProvider {
    fun provideContext(): App
    fun provideActiveActivityHolder(): ActiveActivityHolder
}

@[PerApplication Component(
    modules = [MainToolsModule::class]
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
            fun init(app: App): MainToolsProvider = DaggerMainToolsComponent.builder().app(app).build()
        }
    }
}

@Module()
internal class MainToolsModule {
    @Provides
    @PerApplication
    fun provideActiveActivityHolder(): ActiveActivityHolder = ActiveActivityHolder()
}
