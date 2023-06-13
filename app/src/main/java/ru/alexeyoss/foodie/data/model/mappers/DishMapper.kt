package ru.alexeyoss.foodie.data.model.mappers

import ru.alexeyoss.foodie.data.model.network.DishDTO
import ru.alexeyoss.foodie.data.model.ui.UiDish

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