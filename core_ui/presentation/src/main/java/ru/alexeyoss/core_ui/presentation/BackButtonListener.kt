package ru.alexeyoss.core_ui.presentation

/**
 * Base interface for BackButtonClicks handling
 * */

interface BackButtonListener {
    fun onBackPressed(): Boolean
}