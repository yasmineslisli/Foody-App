package com.example.foodyapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodyapp.FoodModel
import com.example.foodyapp.MainActivity
import com.example.foodyapp.R
import com.example.foodyapp.RecipeDetailsActivity

class FoodAdapter(
    val context: MainActivity,
    var foodList: List<FoodModel>,
    private val layoutId:Int
): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage = view.findViewById<ImageView>(R.id.image_item)
        val foodName:TextView? =  view.findViewById<TextView>(R.id.food_name)
        val foodDescrip:TextView? = view.findViewById<TextView>(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFood = foodList[position]

        // Safely load image with null check
        holder.foodImage?.let { imageView ->
            Glide.with(context)
                .load(currentFood.image)
                .fallback(R.drawable.burger)
                .error(R.drawable.burger)
                .into(imageView)
        }

        holder.foodName?.text = currentFood.name
        holder.foodDescrip?.text = currentFood.description

        holder.itemView.setOnClickListener {
            RecipeDetailsActivity(this, currentFood).show()
        }
    }

    override fun getItemCount(): Int = foodList.size

    fun updateList(newList: List<FoodModel>) {
        foodList = newList
        notifyDataSetChanged()
    }
}