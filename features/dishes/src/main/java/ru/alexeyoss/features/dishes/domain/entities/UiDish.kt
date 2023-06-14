package ru.alexeyoss.features.dishes.domain.entities


data class UiDish(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val imageUrl: String,
    val tegs: ArrayList<String>,
    val isFavorite: Boolean,
    val isInCart: Boolean
)