package com.example.mad_assignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class CartAdapter(
    private val context: Context,
    private var cartItems: List<Product>,
    private val onRemoveClick: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cart_product_image)
        val productName: TextView = itemView.findViewById(R.id.cart_product_name)
        val productPrice: TextView = itemView.findViewById(R.id.cart_product_price)
        val removeButton: ImageButton = itemView.findViewById(R.id.btn_remove_item)
        val cardView: MaterialCardView = itemView.findViewById(R.id.cart_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]

        holder.productName.text = product.name
        holder.productPrice.text = String.format("₹%,.2f", product.price)

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.productImage)

        holder.removeButton.setOnClickListener {
            onRemoveClick(position)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateList(newList: List<Product>) {
        cartItems = newList
        notifyDataSetChanged()
    }
}