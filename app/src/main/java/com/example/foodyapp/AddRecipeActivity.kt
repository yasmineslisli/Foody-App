package com.example.foodyapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodyapp.FoodRepository

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var recipeName: EditText
    private lateinit var recipeDescription: EditText
    private lateinit var recipeIngredients: EditText
    private lateinit var recipeInstructions: EditText
    private lateinit var recipeImage: ImageView
    private lateinit var addImageButton: Button
    private lateinit var saveRecipeButton: Button
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        recipeName = findViewById(R.id.recipe_name)
        recipeDescription = findViewById(R.id.recipe_description)
        recipeIngredients = findViewById(R.id.recipe_ingredients)
        recipeInstructions = findViewById(R.id.recipe_instructions)
        recipeImage = findViewById(R.id.recipe_image)
        addImageButton = findViewById(R.id.add_image_button)
        saveRecipeButton = findViewById(R.id.save_recipe_button)

        addImageButton.setOnClickListener {
            openGallery()
        }

        saveRecipeButton.setOnClickListener {
            saveRecipe()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = result.data?.data
            recipeImage.setImageURI(imageUri)
            recipeImage.visibility = View.VISIBLE
        }
    }

    private fun saveRecipe() {
        val name = recipeName.text.toString()
        val description = recipeDescription.text.toString()
        val ingredients = recipeIngredients.text.toString().split(",").map { it.trim() }
        val instructions = recipeInstructions.text.toString().split("\n").map { it.trim() }
        val imageUrl = imageUri?.toString() ?: ""

        if (name.isEmpty() || description.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show()
            return
        }

        val newRecipe = FoodModel(name, description, imageUrl, ingredients, instructions)

        // Call the addRecipe method from FoodRepository to add the new recipe to Firebase
        FoodRepository.Singleton.addRecipe(newRecipe) { success ->
            if (success) {
                Toast.makeText(this, "Recipe saved!", Toast.LENGTH_SHORT).show()
                finish() // Close the activity
            } else {
                Toast.makeText(this, "Failed to save recipe!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
