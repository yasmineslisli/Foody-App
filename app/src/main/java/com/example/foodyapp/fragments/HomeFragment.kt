package com.example.foodyapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyapp.AddRecipeActivity
import com.example.foodyapp.FoodRepository.Singleton.foodList
import com.example.foodyapp.MainActivity
import com.example.foodyapp.R
import com.example.foodyapp.adapter.FoodAdapter
import com.example.foodyapp.adapter.FoodItemDecoration
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment(private val context: MainActivity) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView?.adapter = FoodAdapter(context, foodList, R.layout.item_horizontal_food)

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = FoodAdapter(context, foodList, R.layout.item_vertical_food)
        verticalRecyclerView?.addItemDecoration(FoodItemDecoration())

        val addRecipeButton = view.findViewById<FloatingActionButton>(R.id.fab_add_recipe)
        addRecipeButton.setOnClickListener {
            val intent = Intent(context, AddRecipeActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)?.adapter?.notifyDataSetChanged()
        view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)?.adapter?.notifyDataSetChanged()
    }
}