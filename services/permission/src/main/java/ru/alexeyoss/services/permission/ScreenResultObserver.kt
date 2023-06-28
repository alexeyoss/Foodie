package ru.alexeyoss.services.permission

interface ScreenResultObserver {

    fun addListener(RESULT_KEY: String) {}
    fun removeListener()
}