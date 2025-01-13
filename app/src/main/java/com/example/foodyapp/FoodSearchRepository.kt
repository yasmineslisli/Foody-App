package com.example.foodyapp

import com.example.foodyapp.service.FoodService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FoodSearchRepository {
    private val apiKey = "hu7r73HRYZvodr+Y7ohaPw==o17WmPjv1cBTESkv"
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(FoodService::class.java)

    fun searchRecipes(query: String, callback: (List<RecipeModel>) -> Unit) {
        apiService.searchRecipes(apiKey, query).enqueue(object : Callback<List<RecipeModel>> {
            override fun onResponse(call: Call<List<RecipeModel>>, response: Response<List<RecipeModel>>) {
                if (response.isSuccessful) {
                    callback(response.body() ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<RecipeModel>>, t: Throwable) {
                callback(emptyList())
            }
        })
    }
}