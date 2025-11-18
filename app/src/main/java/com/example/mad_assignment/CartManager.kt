package com.example.mad_assignment

object CartManager {

    private val cartItems = mutableListOf<Product>()

    fun addProduct(product: Product) {
        cartItems.add(product)
    }

    fun getCartItems(): List<Product> {
        return cartItems.toList()
    }

    fun removeProductAt(position: Int) {
        if (position in cartItems.indices) {
            cartItems.removeAt(position)
        }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getCartSize(): Int {
        return cartItems.size
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price }
    }
}