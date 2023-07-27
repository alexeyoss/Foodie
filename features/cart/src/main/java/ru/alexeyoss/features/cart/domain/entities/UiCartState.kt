package ru.alexeyoss.features.cart.domain.entities

/**
 * Describe UI data state of CartFragment
 */
data class UiCartState(
    val cartItems: List<UiCartItem>? = null,
    val totalSum: Int? = null
)
