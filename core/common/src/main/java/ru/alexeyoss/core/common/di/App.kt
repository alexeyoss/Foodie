package ru.alexeyoss.core.common.di

import android.content.Context

/**
 * Base interface for providing AppContext
 * */
interface App {
    fun getApplicationContext(): Context
}

