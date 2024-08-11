package com.example.cocktailsapp.Favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cocktailsapp.Cocktail.view.CocktailAdapter
import com.example.cocktailsapp.Cocktail.model.CocktailResponse
import com.example.cocktailsapp.R


class FavoriteFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: CocktailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.listView)

        adapter = CocktailAdapter(requireContext(), mutableListOf(), sharedViewModel)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedCocktail = sharedViewModel.favoriteCocktails.value?.get(position)
            selectedCocktail?.let {
                if (isFavorite(it)) {
                    sharedViewModel.removeFavorite(it)
                } else {
                    sharedViewModel.addFavorite(it)
                }
            }
        }

        sharedViewModel.favoriteCocktails.observe(viewLifecycleOwner, Observer { cocktails ->
            adapter.clear()
            adapter.addAll(cocktails)
            adapter.notifyDataSetChanged()
        })
    }

    private fun isFavorite(cocktail: CocktailResponse): Boolean {
        return sharedViewModel.favoriteCocktails.value?.contains(cocktail) == true
    }
}