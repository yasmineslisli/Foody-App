package com.example.foodyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodyapp.FoodSearchRepository
import com.example.foodyapp.R
import com.example.foodyapp.SearchViewModel
import com.example.foodyapp.SearchState
import com.example.foodyapp.adapter.RecipeAdapter

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.searchRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        errorText = view.findViewById(R.id.errorText)

        // Setup RecyclerView
        recipeAdapter = RecipeAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeAdapter
        }

        // Initialize ViewModel
        viewModel = SearchViewModel(FoodSearchRepository())

        // Observe search state
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    errorText.visibility = View.GONE
                }
                is SearchState.Success -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    recipeAdapter.updateRecipes(state.recipes)
                }
                is SearchState.Error -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    errorText.text = state.message
                }
                SearchState.Empty -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    errorText.text = "Enter a search term"
                }
            }
        }

        // Setup search listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchRecipes(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: Implement real-time search
                // newText?.let { viewModel.searchRecipes(it) }
                return true
            }
        })
    }
}