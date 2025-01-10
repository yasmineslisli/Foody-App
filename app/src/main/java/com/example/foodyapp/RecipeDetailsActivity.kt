package com.example.foodyapp

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.foodyapp.adapter.FoodAdapter

class RecipeDetailsActivity(
    private val adapter: FoodAdapter,
    private val food: FoodModel
) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_recipe_details)

        // Find views
        val titleTextView = findViewById<TextView>(R.id.recipe_title)
        val descriptionTextView = findViewById<TextView>(R.id.recipe_description)
        val ingredientsTextView = findViewById<TextView>(R.id.ingredients_text)
        val imageView = findViewById<ImageView>(R.id.recipe_image)
        val instructionsTextView = findViewById<TextView>(R.id.instructions_text)

        // Set data
        titleTextView?.text = food.name
        descriptionTextView?.text = food.description

        // Format ingredients list
        val ingredientsList = food.ingredients.joinToString("\n") { "• $it" }
        ingredientsTextView?.text = ingredientsList

        //instructions
        val instructionsList = food.instructions.joinToString("\n") { "• $it" }
        instructionsTextView?.text = instructionsList

        // Load image using Glide
        imageView?.let { imgView ->
            Glide.with(context)
                .load(food.image)
                .fallback(R.drawable.burger)
                .error(R.drawable.burger)
                .into(imgView)
        }
    }
}

