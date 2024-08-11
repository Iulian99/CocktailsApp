package com.example.cocktailsapp.Setttings.Questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailsapp.R


class QuestionsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var faqAdapter: FaqAdapter
    private lateinit var faqList: List<FaqItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_questions, container, false)

        context?.let { ctx ->
            recyclerView = view.findViewById(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(ctx)

            initData()
            faqAdapter = FaqAdapter(faqList)
            recyclerView.adapter = faqAdapter
        }

        return view
    }

    private fun initData() {
        faqList = listOf(
            FaqItem("How do I search for a specific cocktail?", "To search for a specific cocktail, use the search bar at the top of the fragment. Enter the name or main ingredients of the cocktail you are looking for, and the cocktail list will automatically update with the matching results."),
            FaqItem("How do I add a cocktail to favorites?", "To add a cocktail to your favorites, simply tap the heart icon next to the cocktail's name in the list. If the cocktail is already in your favorites, the icon will be filled."),
            FaqItem("How do I remove a cocktail from my favorites?", "To remove a cocktail from your favorites, tap the heart icon next to the cocktail's name in the list. The icon will become empty, indicating that the cocktail has been removed from your favorites."),
            FaqItem("What happens if I delete a cocktail from my favorites? Can I add it again?", "If you delete a cocktail from your favorites, it will be removed, but you can add it back at any time by following the same steps to add it to favorites from the cocktails fragment."),
            FaqItem("Why don't I see any updates in my favorites list?", "The favorites list updates automatically when you add or remove a cocktail. If you don't see the updates, ensure that the favorites fragment is refreshed or revisit it from the app's menu."),
            FaqItem("What happens if I return to the cocktails fragment after adding a cocktail to favorites?", "Cocktails added to favorites will remain saved and will be available in your favorites list even after you return to the cocktails fragment. The cocktails fragment and favorites list are automatically synchronized."),
            FaqItem("Can I search for cocktails in my favorites list?", "Currently, the search functionality is only available for the entire cocktail list in the main fragment. There is no specific search functionality for the favorites list."),
        )
    }
}