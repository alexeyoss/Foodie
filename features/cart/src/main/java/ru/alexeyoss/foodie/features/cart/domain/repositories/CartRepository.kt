package ru.alexeyoss.foodie.features.cart.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.features.cart.domain.entities.UiCartItem

interface CartRepository {
    suspend fun getCartItems(): Flow<Container<List<UiCartItem>>>
}