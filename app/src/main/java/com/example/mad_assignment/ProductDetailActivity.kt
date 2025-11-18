package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var currentProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val cartButton = findViewById<ImageButton>(R.id.cart_button)
        cartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val productNameTextView = findViewById<TextView>(R.id.product_name)
        val productDetailsTextView = findViewById<TextView>(R.id.product_details)
        val productImageView = findViewById<ImageView>(R.id.product_image)
        val productPriceTextView = findViewById<TextView>(R.id.product_price)
        val btnAddToCart = findViewById<MaterialButton>(R.id.btn_add_to_cart)
        val btnBuyProduct = findViewById<MaterialButton>(R.id.btn_buy_product)

        val productId = intent.getStringExtra("PRODUCT_ID") ?: ""
        val productName = intent.getStringExtra("PRODUCT_NAME") ?: ""
        val productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION") ?: ""
        val productImageUrl = intent.getStringExtra("PRODUCT_IMAGE_URL") ?: ""
        val productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val productCategory = intent.getStringExtra("PRODUCT_CATEGORY") ?: ""

        currentProduct = Product(
            id = productId,
            name = productName,
            category = productCategory,
            price = productPrice,
            imageUrl = productImageUrl,
            description = productDescription
        )

        productNameTextView.text = productName
        productDetailsTextView.text = productDescription
        supportActionBar?.title = productName
        productPriceTextView.text = String.format("₹%,.2f", productPrice)

        Glide.with(this)
            .load(productImageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(productImageView)

        btnAddToCart.setOnClickListener {
            CartManager.addProduct(currentProduct)
            Toast.makeText(this, "${productName} added to cart", Toast.LENGTH_SHORT).show()
        }

        btnBuyProduct.setOnClickListener {
            CartManager.addProduct(currentProduct)
            Toast.makeText(this, "Proceeding to checkout...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}