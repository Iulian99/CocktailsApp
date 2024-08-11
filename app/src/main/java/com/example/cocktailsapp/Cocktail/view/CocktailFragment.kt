package com.example.cocktailsapp.Cocktail.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.cocktailsapp.Favorite.SharedViewModel
import com.example.cocktailsapp.R
import com.example.cocktailsapp.Cocktail.viewModel.CocktailViewModel

class CocktailFragment : Fragment() {
    private val cocktailViewModel: CocktailViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var adapter: CocktailAdapter
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cocktail, container, false)

        val listView = view.findViewById<ListView>(R.id.listView)
        searchView = view.findViewById(R.id.search_view)
        progressBar = view.findViewById(R.id.progress_bar)

        adapter = CocktailAdapter(requireContext(), mutableListOf(), sharedViewModel)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedCocktail = adapter.getItem(position)
            selectedCocktail?.let {
                sharedViewModel.addFavorite(it)
                Log.d("CocktailFragment", "Cocktail adăugat la favorite: ${it.name}")
            }
        }

        cocktailViewModel.cocktails.observe(viewLifecycleOwner) { cocktails ->
            if (cocktails.isNotEmpty()) {
                adapter.clear()
                adapter.addAll(cocktails.take(10))
                adapter.notifyDataSetChanged()
            } else {
                Log.d("CocktailFragment", "Lista de cocktailuri este goală")
            }
        }

        cocktailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Configurarea SearchView pentru a căuta în API
        setupSearchView()

        // Inițializează lista de cocktailuri cu un termen implicit
        cocktailViewModel.fetchCocktails(name = "a")

        return view
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    cocktailViewModel.fetchCocktails(name = it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    // Filtrarea listei de cocktailuri în timp real
                    val filteredCocktails = cocktailViewModel.cocktails.value?.filter {
                        it.name.contains(newText, ignoreCase = true)
                    } ?: emptyList()

                    // Actualizarea adapterului cu lista filtrată
                    adapter.clear()
                    adapter.addAll(filteredCocktails)
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })
    }
}
