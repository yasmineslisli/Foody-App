package com.example.foodyapp

import java.io.Serializable

data class FoodModel(
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList()
) : Serializable

