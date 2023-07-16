package ru.alexeyoss.core.common.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.alexeyoss.core.common.di.scope.PerApplication

interface App {
    fun getApplicationContext(): Context
}

interface MainToolsProvider {
    fun provideContext(): App
}

@[PerApplication Component]
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