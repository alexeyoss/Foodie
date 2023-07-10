package ru.alexeyoss.features.cart.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.features.cart.domain.entities.UiCartItem

interface CartRepository {
    suspend fun getCartItems(): Flow<Container<List<UiCartItem>>>
}