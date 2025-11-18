package com.example.mad_assignment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import kotlin.math.abs

class CompareActivity : AppCompatActivity() {

    private lateinit var allProducts: List<Product>
    private lateinit var categoryProducts: List<Product>

    private var selectedProduct: Product? = null

    private lateinit var flipkartProductImage: ImageView
    private lateinit var flipkartProductName: TextView
    private lateinit var flipkartProductDesc: TextView
    private lateinit var flipkartPrice: TextView

    private lateinit var amazonProductImage: ImageView
    private lateinit var amazonProductName: TextView
    private lateinit var amazonProductDesc: TextView
    private lateinit var amazonPrice: TextView

    private lateinit var resultCard: MaterialCardView
    private lateinit var resultText: TextView
    private lateinit var resultSavings: TextView
    private lateinit var compareButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val category = intent.getStringExtra("CATEGORY") ?: "Clothing"
        allProducts = loadProductsFromJson()
        categoryProducts = allProducts.filter { it.category.equals(category, ignoreCase = true) }

        initializeViews()
        setupButtons()
    }

    private fun initializeViews() {
        flipkartProductImage = findViewById(R.id.flipkart_product_image)
        flipkartProductName = findViewById(R.id.flipkart_product_name)
        flipkartProductDesc = findViewById(R.id.flipkart_product_desc)
        flipkartPrice = findViewById(R.id.flipkart_price)

        amazonProductImage = findViewById(R.id.amazon_product_image)
        amazonProductName = findViewById(R.id.amazon_product_name)
        amazonProductDesc = findViewById(R.id.amazon_product_desc)
        amazonPrice = findViewById(R.id.amazon_price)

        resultCard = findViewById(R.id.result_card)
        resultText = findViewById(R.id.result_text)
        resultSavings = findViewById(R.id.result_savings)
        compareButton = findViewById(R.id.btn_compare)
    }

    private fun setupButtons() {
        findViewById<MaterialButton>(R.id.btn_select_product).setOnClickListener {
            showProductSelectionDialog()
        }

        compareButton.setOnClickListener {
            compareProducts()
        }
    }

    private fun showProductSelectionDialog() {
        val productNames = categoryProducts.map { it.name }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Select Product to Compare")
            .setItems(productNames) { dialog, which ->
                selectedProduct = categoryProducts[which]
                updateBothCards()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateBothCards() {
        val product = selectedProduct ?: return

        val flipkartPriceValue = product.price
        val amazonPriceValue = product.price * (0.85 + Math.random() * 0.30)

        flipkartProductName.text = product.name
        flipkartProductDesc.text = product.description
        flipkartPrice.text = String.format("₹%,.2f", flipkartPriceValue)

        flipkartProductImage.visibility = View.VISIBLE
        flipkartProductDesc.visibility = View.VISIBLE
        flipkartPrice.visibility = View.VISIBLE

        Glide.with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(flipkartProductImage)

        amazonProductName.text = product.name
        amazonProductDesc.text = product.description
        amazonPrice.text = String.format("₹%,.2f", amazonPriceValue)

        amazonProductImage.visibility = View.VISIBLE
        amazonProductDesc.visibility = View.VISIBLE
        amazonPrice.visibility = View.VISIBLE

        Glide.with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(amazonProductImage)

        compareButton.visibility = View.VISIBLE

        resultCard.visibility = View.GONE

        Toast.makeText(this, "Product loaded for both platforms", Toast.LENGTH_SHORT).show()
    }

    private fun compareProducts() {
        if (selectedProduct == null) {
            Toast.makeText(this, "Please select a product first", Toast.LENGTH_SHORT).show()
            return
        }

        val flipkartPriceText = flipkartPrice.text.toString().replace("₹", "").replace(",", "").trim()
        val amazonPriceText = amazonPrice.text.toString().replace("₹", "").replace(",", "").trim()

        val flipkartP = flipkartPriceText.toDoubleOrNull() ?: 0.0
        val amazonP = amazonPriceText.toDoubleOrNull() ?: 0.0

        val difference = abs(flipkartP - amazonP)

        resultCard.visibility = View.VISIBLE

        when {
            flipkartP < amazonP -> {
                resultText.text = "🎉 Flipkart offers better price!"
                resultSavings.text = String.format("Save ₹%,.2f", difference)
                resultSavings.setTextColor(getColor(android.R.color.holo_green_dark))

                val percentage = (difference / amazonP) * 100
                Toast.makeText(
                    this,
                    "Flipkart is ${String.format("%.1f", percentage)}% cheaper!",
                    Toast.LENGTH_LONG
                ).show()
            }
            amazonP < flipkartP -> {
                resultText.text = "🎉 Amazon offers better price!"
                resultSavings.text = String.format("Save ₹%,.2f", difference)
                resultSavings.setTextColor(getColor(android.R.color.holo_orange_dark))

                val percentage = (difference / flipkartP) * 100
                Toast.makeText(
                    this,
                    "Amazon is ${String.format("%.1f", percentage)}% cheaper!",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                resultText.text = "Both platforms have same price!"
                resultSavings.text = "No savings"
                resultSavings.setTextColor(getColor(android.R.color.holo_blue_dark))
                Toast.makeText(this, "Prices are equal on both platforms", Toast.LENGTH_SHORT).show()
            }
        }

        val scrollView = findViewById<androidx.core.widget.NestedScrollView>(R.id.scroll_view_content)
        scrollView?.post {
            scrollView.smoothScrollTo(0, resultCard.top)
        }
    }

    private fun loadProductsFromJson(): List<Product> {
        return try {
            val inputStream = assets.open("products.json")
            val reader = InputStreamReader(inputStream)
            val productListType = object : TypeToken<List<Product>>() {}.type
            Gson().fromJson(reader, productListType)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}