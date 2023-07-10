package ru.alexeyoss.foodie.mediators.cart.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.features.cart.domain.entities.UiCartItem
import ru.alexeyoss.features.cart.domain.repositories.CartRepository
import javax.inject.Inject

class AdapterCartRepository
@Inject
constructor() : CartRepository {
    override suspend fun getCartItems(): Flow<Container<List<UiCartItem>>> {
        // TODO
        return flow {
            Container.Success(
                listOf(
                    UiCartItem(
                        id = 1,
                        name = "",
                        price = 0,
                        weight = 0,
                        description = "",
                        imageUrl = "",
                        tegs = arrayListOf(),
                        amount = 0
                    )
                )
            )
        }
    }
}