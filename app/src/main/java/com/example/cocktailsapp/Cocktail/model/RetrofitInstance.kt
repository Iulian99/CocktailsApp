package com.example.cocktailsapp.Cocktail.model


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.api-ninjas.com/v1/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }
}