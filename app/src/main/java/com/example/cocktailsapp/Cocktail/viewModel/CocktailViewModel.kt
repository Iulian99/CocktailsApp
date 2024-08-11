package com.example.cocktailsapp.Cocktail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.Cocktail.model.CocktailResponse
import com.example.cocktailsapp.Cocktail.model.RetrofitInstance
import kotlinx.coroutines.launch


class CocktailViewModel : ViewModel() {

    private val _cocktails = MutableLiveData<List<CocktailResponse>>()
    val cocktails: LiveData<List<CocktailResponse>> get() = _cocktails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchCocktails(name: String? = null, ingredients: String? = null) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCocktails(name, ingredients)
                if (response.isSuccessful) {
                    _cocktails.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to fetch cocktails: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching cocktails: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}