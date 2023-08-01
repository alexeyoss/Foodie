package ru.alexeyoss.core.common.di

import android.content.Context

/**
 * Base interface for providing AppContext
 * */
interface App {
    fun getApplicationContext(): Context

    // TODO implement to get rid of boilerplate deps providing for features
    // fun getApplicationComponent(): AppComponent
}

