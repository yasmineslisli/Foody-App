package com.example.foodyapp.service

import com.example.foodyapp.RecipeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FoodService {
    @GET("v1/recipe")
    fun searchRecipes(
        @Header("X-Api-Key") apiKey: String,
        @Query("query") query: String
    ): Call<List<RecipeModel>>
}