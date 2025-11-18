package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyCartTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var checkoutButton: MaterialButton
    private lateinit var clearCartButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_cart)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        recyclerView = findViewById(R.id.recycler_view_cart)
        emptyCartTextView = findViewById(R.id.text_empty_cart)
        totalPriceTextView = findViewById(R.id.text_total_price)
        checkoutButton = findViewById(R.id.btn_checkout)
        clearCartButton = findViewById(R.id.btn_clear_cart)

        setupRecyclerView()
        updateCartUI()

        clearCartButton.setOnClickListener {
            showClearCartDialog()
        }

        checkoutButton.setOnClickListener {
            if (CartManager.getCartItems().isNotEmpty()) {
                Toast.makeText(this, "Proceeding to checkout...", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(this, CartManager.getCartItems().toMutableList()) { position ->
            removeItem(position)
        }
        recyclerView.adapter = cartAdapter
    }

    private fun removeItem(position: Int) {
        CartManager.removeProductAt(position)
        cartAdapter.updateList(CartManager.getCartItems())
        updateCartUI()
        Toast.makeText(this, "Item removed from cart", Toast.LENGTH_SHORT).show()
    }

    private fun updateCartUI() {
        val cartItems = CartManager.getCartItems()

        if (cartItems.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyCartTextView.visibility = View.VISIBLE
            totalPriceTextView.visibility = View.GONE
            checkoutButton.visibility = View.GONE
            clearCartButton.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyCartTextView.visibility = View.GONE
            totalPriceTextView.visibility = View.VISIBLE
            checkoutButton.visibility = View.VISIBLE
            clearCartButton.visibility = View.VISIBLE

            val total = cartItems.sumOf { it.price }
            totalPriceTextView.text = String.format("Total: ₹%,.2f", total)
        }
    }

    private fun showClearCartDialog() {
        AlertDialog.Builder(this)
            .setTitle("Clear Cart")
            .setMessage("Are you sure you want to remove all items from your cart?")
            .setPositiveButton("Yes") { _, _ ->
                CartManager.clearCart()
                cartAdapter.updateList(emptyList())
                updateCartUI()
                Toast.makeText(this, "Cart cleared", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        cartAdapter.updateList(CartManager.getCartItems())
        updateCartUI()
    }
}