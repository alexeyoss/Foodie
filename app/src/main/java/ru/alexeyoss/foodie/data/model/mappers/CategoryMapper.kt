package ru.alexeyoss.foodie.data.model.mappers

import ru.alexeyoss.foodie.data.model.network.CategoryDTO
import ru.alexeyoss.features.categories.domain.entities.UiCategory

class CategoryMapper : BaseMapper<UiCategory, CategoryDTO> {
    override fun mapToDomainModel(foreignModel: CategoryDTO): UiCategory {
        return UiCategory(
            id = foreignModel.id,
            name = foreignModel.name,
            image_url = foreignModel.image_url,
        )
    }

    override fun mapToForeignModel(domainModel: UiCategory): CategoryDTO {
        return CategoryDTO(
            id = domainModel.id,
            name = domainModel.name,
            image_url = domainModel.image_url
        )
    }
}