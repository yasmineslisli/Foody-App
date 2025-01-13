package com.example.foodyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class SearchState {
    object Loading : SearchState()
    data class Success(val recipes: List<RecipeModel>) : SearchState()
    data class Error(val message: String) : SearchState()
    object Empty : SearchState()
}

class SearchViewModel(private val repository: FoodSearchRepository) : ViewModel() {
    private val _searchState = MutableLiveData<SearchState>(SearchState.Empty)
    val searchState: LiveData<SearchState> = _searchState

    fun searchRecipes(query: String) {
        if (query.isBlank()) {
            _searchState.value = SearchState.Empty
            return
        }

        _searchState.value = SearchState.Loading
        repository.searchRecipes(query) { recipes ->
            _searchState.value = when {
                recipes.isEmpty() -> SearchState.Error("No recipes found")
                else -> SearchState.Success(recipes)
            }
        }
    }
}
