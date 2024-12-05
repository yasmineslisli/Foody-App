package com.example.foodyapp

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foodyapp.adapter.FoodAdapter

class RecipeDetailsActivity( private val adapter : FoodAdapter): Dialog(adapter.context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature (Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_recipe_details)
    }

}

