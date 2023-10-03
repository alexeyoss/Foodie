package ru.alexeyoss.foodie.features.categories.presentation

import ru.alexeyoss.foodie.features.categories.domain.entities.UiCategory

sealed interface CategoriesUiState {
    data class Success(val data: List<UiCategory>) : CategoriesUiState
    data class Error(val error: Exception) : CategoriesUiState
    object Loading : CategoriesUiState
    object Initial : CategoriesUiState
}