package com.example.foodyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.foodyapp.adapter.RecipeAdapter
import com.example.foodyapp.fragments.HomeFragment
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchView: SearchView
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        setupViews()

        viewModel = SearchViewModel(FoodSearchRepository())

        setupRecyclerView()

        setupSearch()

        setupObservers()

        loadHomeFragment()
    }

    private fun setupViews() {
        searchView = findViewById(R.id.search_view)
        searchResultsRecyclerView = findViewById(R.id.searchRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(emptyList())
        searchResultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recipeAdapter
        }
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchRecipes(it)
                    showSearchResults()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    showHomeFragment()
                }
                return true
            }
        })

        searchView.setOnCloseListener {
            showHomeFragment()
            true
        }
    }

    private fun setupObservers() {
        viewModel.searchState.observe(this) { state ->
            when (state) {
                is SearchState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    searchResultsRecyclerView.visibility = View.GONE
                    errorText.visibility = View.GONE
                    supportFragmentManager.fragments.forEach { it.view?.visibility = View.GONE }
                }
                is SearchState.Success -> {
                    progressBar.visibility = View.GONE
                    searchResultsRecyclerView.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    supportFragmentManager.fragments.forEach { it.view?.visibility = View.GONE }
                    recipeAdapter.updateRecipes(state.recipes)
                }
                is SearchState.Error -> {
                    progressBar.visibility = View.GONE
                    searchResultsRecyclerView.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    errorText.text = state.message
                    supportFragmentManager.fragments.forEach { it.view?.visibility = View.GONE }
                }
                SearchState.Empty -> {
                    showHomeFragment()
                }

                else -> {}
            }
        }
    }

    private fun showSearchResults() {
        supportFragmentManager.fragments.forEach { it.view?.visibility = View.GONE }
        searchResultsRecyclerView.visibility = View.VISIBLE
    }

    private fun showHomeFragment() {
        searchResultsRecyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorText.visibility = View.GONE
        loadHomeFragment()
    }

    private fun loadHomeFragment() {
        val repo = FoodRepository()
        repo.updateData {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}