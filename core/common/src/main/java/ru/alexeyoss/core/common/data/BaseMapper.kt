package ru.alexeyoss.core.common.data

interface BaseMapper<DomainModel, ForeignModel> {
    fun mapToDomainModel(foreignModel: ForeignModel): DomainModel
    fun mapToForeignModel(domainModel: DomainModel): ForeignModel

    fun mapEntityList(entities: List<ForeignModel>): List<DomainModel> {
        return entities.map { mapToDomainModel(it) }
    }
}