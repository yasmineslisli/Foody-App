package com.example.foodyapp
import android.util.Log
import com.example.foodyapp.FoodRepository.Singleton.databaseRef
import com.example.foodyapp.FoodRepository.Singleton.foodList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodRepository {

    object Singleton{
        val databaseRef = FirebaseDatabase.getInstance().getReference("FoodScroll")

        val foodList = arrayListOf<FoodModel>()

        fun addRecipe(newRecipe: FoodModel, callback: (Boolean) -> Unit) {
            val key = databaseRef.push().key // Generates a unique key for the new recipe
            if (key != null) {
                databaseRef.child(key).setValue(newRecipe).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Recipe added successfully
                        foodList.add(newRecipe)
                        callback(true)
                    } else {
                        // Failed to add recipe
                        Log.e("FoodRepository", "Failed to add recipe: ${task.exception?.message}")
                        callback(false)
                    }
                }
            } else {
                // If the key is null, we couldn't generate a unique key
                callback(false)
            }
        }
    }

    fun updateData(callback: () -> Unit) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                foodList.clear()
                for (ds in snapshot.children) {
                    val food = ds.getValue(FoodModel::class.java)
                    if (food != null) {
                        foodList.add(food)
                    }
                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FoodRepository", "Database error: ${error.message}")
            }
        })
    }

}

