package ru.alexeyoss.foodie.core.common.di.modules

import dagger.Module
import dagger.Provides
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder

@Module
internal object MainToolsModule {
    @Provides
    fun provideActiveActivityHolder(): ActiveActivityHolder = ActiveActivityHolder()
}
