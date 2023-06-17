package ru.alexeyoss.foodie.mediators.dishes.mappers

import ru.alexeyoss.network.dishes.models.DishDTO
import ru.alexeyoss.features.dishes.domain.entities.UiDish
import ru.alexeyoss.core.common.BaseMapper

class DishMapper : BaseMapper<UiDish, DishDTO> {
    override fun mapToDomainModel(foreignModel: DishDTO): UiDish {
        return UiDish(
            id = foreignModel.id,
            name = foreignModel.name,
            price = foreignModel.price,
            weight = foreignModel.weight,
            description = foreignModel.description,
            imageUrl = foreignModel.imageUrl,
            tegs = foreignModel.tegs,
            isFavorite = false,
            isInCart = false
        )
    }

    override fun mapToForeignModel(domainModel: UiDish): DishDTO {
        return DishDTO(
            id = domainModel.id,
            name = domainModel.name,
            price = domainModel.price,
            weight = domainModel.weight,
            description = domainModel.description,
            imageUrl = domainModel.imageUrl,
            tegs = domainModel.tegs
        )
    }

}