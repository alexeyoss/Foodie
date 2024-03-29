package ru.alexeyoss.foodie.features.cart.domain

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.features.cart.domain.entities.UiCartItem
import ru.alexeyoss.foodie.features.cart.domain.repositories.CartRepository
import javax.inject.Inject

class GetCartItems @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(): Flow<Container<List<UiCartItem>>> = cartRepository.getCartItems()
}
