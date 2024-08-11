package com.example.cocktailsapp.Cocktail.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cocktailsapp.Cocktail.model.CocktailResponse
import com.example.cocktailsapp.Favorite.SharedViewModel
import com.example.cocktailsapp.R

class CocktailAdapter(
    context: Context,
    private val cocktails: List<CocktailResponse>,
    private val sharedViewModel: SharedViewModel
) : ArrayAdapter<CocktailResponse>(context, 0, cocktails) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_cocktail, parent, false)
        val cocktail = getItem(position)

        val nameTextView = view.findViewById<TextView>(R.id.cocktail_name)
        val ingredientsTextView = view.findViewById<TextView>(R.id.cocktail_ingredients)
        val instructionsTextView = view.findViewById<TextView>(R.id.cocktail_instructions)
        val favoriteButton = view.findViewById<ImageView>(R.id.favorite_button)

        nameTextView.text = cocktail?.name?.uppercase()
        ingredientsTextView.text = cocktail?.ingredients?.joinToString("\n") { "• $it" }
        instructionsTextView.text = cocktail?.instructions

        // Setează starea inițială a iconiței de favorite
        if (sharedViewModel.isFavorite(cocktail!!)) {
            favoriteButton.setImageResource(R.drawable.heart_icon) // Iconița plină
        }
        else {
            favoriteButton.setImageResource(R.drawable.heart_icon_bl) // Iconița goală
        }

        // Gestionează click-ul pe iconița de favorite
        favoriteButton.setOnClickListener {
            if (sharedViewModel.isFavorite(cocktail)) {
                sharedViewModel.removeFavorite(cocktail)
                favoriteButton.setImageResource(R.drawable.heart_icon)
            } else {
                sharedViewModel.addFavorite(cocktail)
                favoriteButton.setImageResource(R.drawable.heart_icon_bl)
            }

            // Notifică adapterul să actualizeze doar acest rând
            notifyDataSetChanged() // Acest lucru reîncărcă lista
        }
        return view
    }
}