package com.example.cocktailsapp.Cocktail.model

import com.example.cocktailsapp.Cocktail.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CocktailApiService {
    @Headers("X-Api-Key: API_KEY")
    @GET("cocktail")
    suspend fun getCocktails(
        @Query("name") name: String? = null,
        @Query("ingredients") ingredients: String? = null
    ): Response<List<CocktailResponse>>
}