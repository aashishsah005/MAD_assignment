package com.example.mad_assignment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class ProductAdapter(private val context: Context, private var productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val cardView: MaterialCardView = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.name
        holder.productPrice.text = String.format("₹%,.2f", product.price)

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.productImage)

        holder.cardView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)

            intent.putExtra("PRODUCT_ID", product.id)
            intent.putExtra("PRODUCT_NAME", product.name)
            intent.putExtra("PRODUCT_PRICE", product.price)
            intent.putExtra("PRODUCT_IMAGE_URL", product.imageUrl)
            intent.putExtra("PRODUCT_DESCRIPTION", product.description)
            intent.putExtra("PRODUCT_CATEGORY", product.category)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}