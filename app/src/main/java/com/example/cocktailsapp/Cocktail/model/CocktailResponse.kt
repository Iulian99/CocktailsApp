package com.example.cocktailsapp.Cocktail.model

data class CocktailResponse(
    val name: String,
    val ingredients: List<String>,
    val instructions: String
)