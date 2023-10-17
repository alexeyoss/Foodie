package ru.alexeyoss.foodie.activity

sealed interface MainActivitySideEffects {
    object Initial : MainActivitySideEffects
    object ShowPermissionRational : MainActivitySideEffects
}
