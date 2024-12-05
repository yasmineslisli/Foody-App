package com.example.foodyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyapp.MainActivity
import com.example.foodyapp.R
import com.example.foodyapp.RecipeDetailsActivity

class FoodAdapter(val context: MainActivity, private val layoutId:Int): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            RecipeDetailsActivity(this).show()
        }
    }

    override fun getItemCount(): Int = 5
}