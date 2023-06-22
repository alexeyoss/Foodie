package ru.alexeyoss.features.dishes.domain.models

import ru.alexeyoss.features.dishes.presentation.dishes.adapters.Item

data class UiFilterDTO(
    val value: String,
    val displayText: String,
    val isChecked: Boolean
) : Item