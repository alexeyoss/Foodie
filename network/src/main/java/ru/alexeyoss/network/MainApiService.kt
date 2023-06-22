package ru.alexeyoss.network

import retrofit2.http.GET
import ru.alexeyoss.network.categories.models.CategoryListDTO
import ru.alexeyoss.network.dishes.models.DishesListDTO

interface MainApiService {

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryListDTO

    @GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getDishes(): DishesListDTO

}