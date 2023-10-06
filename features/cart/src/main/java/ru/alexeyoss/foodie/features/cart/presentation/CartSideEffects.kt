package ru.alexeyoss.foodie.features.cart.presentation

sealed interface CartSideEffects {
    data class Error(val error: Exception) : CartSideEffects
    object Loading : CartSideEffects
    object Initial : CartSideEffects
}