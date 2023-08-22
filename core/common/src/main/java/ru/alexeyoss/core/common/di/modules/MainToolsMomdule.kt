package ru.alexeyoss.core.common.di.modules

import dagger.Module
import dagger.Provides
import ru.alexeyoss.core.common.activity.ActiveActivityHolder

@Module
internal object MainToolsModule {
    @Provides
    fun provideActiveActivityHolder(): ActiveActivityHolder = ActiveActivityHolder()
}
