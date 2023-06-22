package ru.alexeyoss.features.dishes.presentation


sealed interface DishesSideEffects {
    data class Error(val error: Exception) : DishesSideEffects
    object Loading : DishesSideEffects
    object Initial : DishesSideEffects
}