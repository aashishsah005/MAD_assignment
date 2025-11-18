package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class ElectronicsActivity : AppCompatActivity() {

    private lateinit var allProducts: List<Product>
    private lateinit var filteredProducts: List<Product>
    private lateinit var productAdapter: ProductAdapter
    private lateinit var searchResultsAdapter: ProductAdapter

    private var currentPlatform = "Flipkart"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_electronics)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<ImageButton>(R.id.cart_button).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        allProducts = loadProductsFromJson()
        filteredProducts = allProducts.filter { it.category.equals("Electronics", ignoreCase = true) }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(this, filteredProducts)
        recyclerView.adapter = productAdapter

        setupChips()
        setupSearch()

        Log.d("ElectronicsActivity", "Found: ${filteredProducts.size} electronics products")
    }

    private fun setupChips() {
        val chipGroup = findViewById<ChipGroup>(R.id.chip_group)

        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                when (checkedIds[0]) {
                    R.id.chip_flipkart -> {
                        currentPlatform = "Flipkart"
                        Toast.makeText(this, "Showing Flipkart products", Toast.LENGTH_SHORT).show()
                    }
                    R.id.chip_amazon -> {
                        currentPlatform = "Amazon"
                        Toast.makeText(this, "Showing Amazon products", Toast.LENGTH_SHORT).show()
                    }
                    R.id.chip_compare -> {
                        val intent = Intent(this, CompareActivity::class.java)
                        intent.putExtra("CATEGORY", "Clothing")
                        startActivity(intent)
                        when (currentPlatform) {
                            "Flipkart" -> findViewById<Chip>(R.id.chip_flipkart).isChecked = true
                            "Amazon" -> findViewById<Chip>(R.id.chip_amazon).isChecked = true
                        }
                    }
                }
            }
        }
    }

    private fun setupSearch() {
        val searchBar = findViewById<SearchBar>(R.id.searchBar)
        val searchView = findViewById<SearchView>(R.id.searchView)
        val searchResultsRecyclerView = findViewById<RecyclerView>(R.id.recyclerView_search_results)

        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        searchResultsAdapter = ProductAdapter(this, emptyList())
        searchResultsRecyclerView.adapter = searchResultsAdapter

        searchView.setupWithSearchBar(searchBar)

        searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    val results = filteredProducts.filter { product ->
                        product.name.contains(query, ignoreCase = true) ||
                                product.description.contains(query, ignoreCase = true)
                    }
                    searchResultsAdapter.updateList(results)
                    Log.d("ElectronicsActivity", "Search: $query, Results: ${results.size}")
                } else {
                    searchResultsAdapter.updateList(emptyList())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadProductsFromJson(): List<Product> {
        return try {
            val inputStream = assets.open("products.json")
            val reader = InputStreamReader(inputStream)
            val productListType = object : TypeToken<List<Product>>() {}.type
            Gson().fromJson(reader, productListType)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ElectronicsActivity", "Error reading products.json: ${e.message}")
            emptyList()
        }
    }
}
