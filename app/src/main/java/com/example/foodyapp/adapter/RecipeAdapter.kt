package com.example.foodyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyapp.R
import com.example.foodyapp.RecipeModel

class RecipeAdapter(
    private var recipes: List<RecipeModel>
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.recipe_title)
        val ingredientsTextView: TextView = view.findViewById(R.id.recipe_ingredients)
        val servingsTextView: TextView = view.findViewById(R.id.recipe_servings)
        val instructionsTextView: TextView = view.findViewById(R.id.recipe_instructions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]

        // Set title
        holder.titleTextView.text = recipe.title.capitalize()

        // Format servings
        holder.servingsTextView.text = "Serves: ${recipe.servings}"

        // Format ingredients with improved cleaning and formatting
        val cleanedIngredients = formatIngredients(recipe.ingredients)
        holder.ingredientsTextView.text = cleanedIngredients

        // Format instructions as numbered steps
        val instructionsList = recipe.instructions
            .split(".")
            .filter { it.isNotBlank() }
            .mapIndexed { index, instruction ->
                "${index + 1}. ${instruction.trim()}"
            }
            .joinToString("\n")
        holder.instructionsTextView.text = instructionsList
    }

    private fun formatIngredients(ingredients: String): String {
        return ingredients
            .split("|")  // Split by the | delimiter
            .map { ingredient ->
                ingredient
                    .replace(";", ",")  // Replace semicolons with commas
                    .replace("/", " / ")  // Add spaces around slashes
                    .split(",")  // Split by comma to separate quantity and notes
                    .map { it.trim() }  // Trim each part
                    .filter { it.isNotEmpty() }  // Remove empty parts
                    .joinToString(" - ")  // Join parts with a dash
            }
            .filter { it.isNotEmpty() }  // Remove empty ingredients
            .map { "• $it" }  // Add bullet points
            .joinToString("\n")  // Join with newlines
            .replace("--or--", "or")  // Clean up the or separator
            .replace("  ", " ")  // Remove double spaces
    }

    override fun getItemCount() = recipes.size

    fun updateRecipes(newRecipes: List<RecipeModel>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}