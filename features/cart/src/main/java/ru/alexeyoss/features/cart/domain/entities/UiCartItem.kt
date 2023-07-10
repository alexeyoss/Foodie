package ru.alexeyoss.features.cart.domain.entities

data class UiCartItem(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val imageUrl: String,
    val tegs: ArrayList<String>,
    val amount: Int
)
