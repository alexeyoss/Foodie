package ru.alexeyoss.network

import retrofit2.http.GET
import ru.alexeyoss.network.models.CategoryListDTO
import ru.alexeyoss.network.models.DishesListDTO

interface MainApiService {

    // https://run.mocky.io/v3/49578eea-ff44-4b8f-8893-525e7f6a6e35
    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryListDTO

    // https://run.mocky.io/v3/ba255bce-3079-45ac-83db-e38cd4be1c95
    @GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getDishes(): DishesListDTO
}