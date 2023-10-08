package ru.alexeyoss.foodie.core.common.di

import android.content.Context

/**
 * Base interface for providing AppContext
 * */
interface AppContextProvider {
    fun getApplicationContext(): Context
}
