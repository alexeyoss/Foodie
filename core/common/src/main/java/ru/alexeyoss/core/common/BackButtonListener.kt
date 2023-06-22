package ru.alexeyoss.core.common

/**
 * Base interface for BackButtonClicks handling
 * */

interface BackButtonListener {
    fun onBackPressed(): Boolean
}