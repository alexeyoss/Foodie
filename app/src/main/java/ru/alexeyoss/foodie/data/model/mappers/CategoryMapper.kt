package ru.alexeyoss.foodie.data.model.mappers

import ru.alexeyoss.foodie.data.model.network.CategoryDTO
import ru.alexeyoss.foodie.data.model.ui.UiCategory

class CategoryMapper : BaseMapper<UiCategory, CategoryDTO> {
    override fun mapToDomainModel(foreignModel: CategoryDTO): UiCategory {
        return UiCategory(
            category = foreignModel
        )
    }

    override fun mapToForeignModel(domainModel: UiCategory): CategoryDTO {
        return CategoryDTO(
            id = domainModel.category.id,
            name = domainModel.category.name,
            imageUrl = domainModel.category.imageUrl
        )
    }
}