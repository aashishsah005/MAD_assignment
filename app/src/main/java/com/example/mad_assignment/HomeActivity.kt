package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class HomeActivity : AppCompatActivity() {

    private val allProducts = mutableListOf<Product>()
    private lateinit var searchResultsAdapter: ProductAdapter
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applyTheme(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        loadProductsFromJson()

        val mainContentContainer = findViewById<ConstraintLayout>(R.id.main_content_container)
        val searchBar = findViewById<SearchBar>(R.id.searchBar)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val searchResultsRecyclerView = findViewById<RecyclerView>(R.id.recyclerView_search_results)

        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        searchResultsAdapter = ProductAdapter(this, emptyList())
        searchResultsRecyclerView.adapter = searchResultsAdapter

        searchView.setupWithSearchBar(searchBar)

        searchView.addTransitionListener { _, _, newState ->
            if (newState == SearchView.TransitionState.SHOWING || newState == SearchView.TransitionState.SHOWN) {
                mainContentContainer.visibility = View.GONE
            } else if (newState == SearchView.TransitionState.HIDING || newState == SearchView.TransitionState.HIDDEN) {
                mainContentContainer.visibility = View.VISIBLE
            }
        }

        searchView.editText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    performSearch(query)
                } else {
                    searchResultsAdapter.updateList(emptyList())
                }
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        setupCategoryCards()
        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.selectedItemId = R.id.nav_home
        updateCartBadge()
    }

    private fun setupCategoryCards() {
        findViewById<CardView>(R.id.clothingCard).setOnClickListener {
            startActivity(Intent(this, ClothingActivity::class.java))
        }

        findViewById<CardView>(R.id.phoneCard).setOnClickListener {
            startActivity(Intent(this, PhoneActivity::class.java))
        }

        findViewById<CardView>(R.id.elecCard).setOnClickListener {
            startActivity(Intent(this, ElectronicsActivity::class.java))
        }

        findViewById<CardView>(R.id.beautyCard).setOnClickListener {
            startActivity(Intent(this, BeautyActivity::class.java))
        }

        findViewById<CardView>(R.id.pharmCard).setOnClickListener {
            startActivity(Intent(this, PharmacyActivity::class.java))
        }

        findViewById<CardView>(R.id.grocCard).setOnClickListener {
            startActivity(Intent(this, GroceriesActivity::class.java))
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_home

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already on home
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadProductsFromJson() {
        try {
            val inputStream = assets.open("products.json")
            val reader = InputStreamReader(inputStream)
            val productListType = object : TypeToken<List<Product>>() {}.type
            val products: List<Product> = Gson().fromJson(reader, productListType)
            allProducts.addAll(products)
            Log.d("JSON_LOAD", "Successfully loaded ${allProducts.size} products.")
        } catch (e: Exception) {
            Log.e("JSON_LOAD", "Error loading products: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun performSearch(query: String) {
        val searchResults = allProducts.filter { product ->
            product.name.contains(query, ignoreCase = true) ||
                    product.category.contains(query, ignoreCase = true) ||
                    product.description.contains(query, ignoreCase = true)
        }

        searchResultsAdapter.updateList(searchResults)

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "No results found for '$query'", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCartBadge() {
        val cartSize = CartManager.getCartSize()
        val badge = bottomNavigationView.getOrCreateBadge(R.id.nav_cart)

        if (cartSize > 0) {
            badge.isVisible = true
            badge.number = cartSize
        } else {
            badge.isVisible = false
        }
    }
}