package com.example.cocktailsapp.Favorite


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailsapp.Cocktail.model.CocktailResponse

class SharedViewModel : ViewModel() {

    private val _favoriteCocktails = MutableLiveData<MutableList<CocktailResponse>>(mutableListOf())
    val favoriteCocktails: LiveData<MutableList<CocktailResponse>> get() = _favoriteCocktails

    fun addFavorite(cocktail: CocktailResponse) {
        _favoriteCocktails.value?.let {
            if (!it.contains(cocktail)) { // se verifica dacă cocktailul este deja în lista de favorite
                it.add(cocktail)
                _favoriteCocktails.value = it // Actualizează LiveData
            }
        }
    }

    fun removeFavorite(cocktail: CocktailResponse) {
        _favoriteCocktails.value?.let {
            it.remove(cocktail)
            _favoriteCocktails.value = it // Actualizează LiveData
        }
    }

    fun isFavorite(cocktail: CocktailResponse): Boolean {
        return _favoriteCocktails.value?.contains(cocktail) == true
    }
}