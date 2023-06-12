package ru.alexeyoss.foodie.data.network

import retrofit2.http.GET
import ru.alexeyoss.foodie.BuildConfig.*
import ru.alexeyoss.foodie.data.model.network.CategoryList
import ru.alexeyoss.foodie.data.model.network.DishesList

interface ApiService {

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryList

    @GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getCategoryDishes(): DishesList

}