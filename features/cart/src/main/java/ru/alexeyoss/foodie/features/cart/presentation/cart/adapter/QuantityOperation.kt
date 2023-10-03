package ru.alexeyoss.foodie.features.cart.presentation.cart.adapter

import ru.alexeyoss.features.cart.R
import ru.alexeyoss.foodie.features.cart.domain.entities.UiCartItem
import ru.alexeyoss.foodie.features.cart.presentation.cart.adapter.QuantityOperation.Minus
import ru.alexeyoss.foodie.features.cart.presentation.cart.adapter.QuantityOperation.Plus

/**
 * Operations under quantity selector [R.layout.quantity_selector_layout]
 * Accumulate amount of clicks on [Plus] or [Minus] views and invoke update screen state scenario
 * */
sealed interface QuantityOperation {
    data class Plus(
        val uiCartItem: UiCartItem,
        val itemPosition: Int
    ) : QuantityOperation

    data class Minus(
        val uiCartItem: UiCartItem,
        val itemPosition: Int
    ) : QuantityOperation
}