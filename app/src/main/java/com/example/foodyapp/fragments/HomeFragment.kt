package com.example.foodyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyapp.MainActivity
import com.example.foodyapp.R
import com.example.foodyapp.adapter.FoodAdapter
import com.example.foodyapp.adapter.FoodItemDecoration


class HomeFragment(private val context: MainActivity) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)

        val horizontalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView?.adapter = FoodAdapter(context,R.layout.item_horizontal_food)

        val verticalRecyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = FoodAdapter(context,R.layout.item_vertical_food)
        verticalRecyclerView?.addItemDecoration(FoodItemDecoration())
        return view
    }
}