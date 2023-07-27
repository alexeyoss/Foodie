package ru.alexeyoss.features.cart.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.alexeyoss.core.common.di.CoroutinesModule
import ru.alexeyoss.features.cart.domain.entities.UiCartItem
import ru.alexeyoss.features.cart.domain.entities.UiCartState
import ru.alexeyoss.features.cart.presentation.cart.adapter.QuantityOperation
import javax.inject.Inject

class UpdateUiCartState
@Inject constructor(
    @CoroutinesModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        listOfCartItems: List<UiCartItem>,
        quantityOperation: QuantityOperation? = null
    ): UiCartState = withContext(defaultDispatcher) {
        return@withContext when (quantityOperation) {
            is QuantityOperation.Minus -> {
                val newCartItemMutableList = listOfCartItems.toMutableList()

                /** Check that diff up to zero. Minus operation can be invoked
                 * If NOT delete product from the cart */
                val amountDiff = quantityOperation.uiCartItem.amount - 1

                if (amountDiff > 0) {
                    newCartItemMutableList[quantityOperation.itemPosition] =
                        quantityOperation.uiCartItem.copy(amount = amountDiff)
                } else {
                    newCartItemMutableList.removeAt(quantityOperation.itemPosition)
                }

                /** Count total sum of products in cart*/
                val totalSum = newCartItemMutableList.sumOf { it.amount * it.price }

                UiCartState(
                    cartItems = newCartItemMutableList.toList(),
                    totalSum = totalSum
                )
            }

            is QuantityOperation.Plus -> {
                val newCartItemMutableList = listOfCartItems.toMutableList()

                // TODO check products balance to purchase. Don't allow to order more than warehouse balance have.

                /** Find certain [UiCartItem] and increment the amount of products*/
                newCartItemMutableList[quantityOperation.itemPosition] = quantityOperation.uiCartItem.copy(
                    amount = quantityOperation.uiCartItem.amount + 1
                )

                /** Count total sum of products in cart*/
                val totalSum = newCartItemMutableList.sumOf { it.amount * it.price }

                UiCartState(
                    cartItems = newCartItemMutableList.toList(),
                    totalSum = totalSum
                )

            }

            null -> {
                val totalSum = listOfCartItems.sumOf { it.amount * it.price }

                UiCartState(
                    cartItems = listOfCartItems,
                    totalSum = totalSum
                )
            }
        }
    }
}
