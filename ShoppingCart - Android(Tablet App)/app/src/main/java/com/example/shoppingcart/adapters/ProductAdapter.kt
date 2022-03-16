package com.example.shoppingcart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.databinding.ProductsCardBinding
import com.example.shoppingcart.models.ProductModel

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productsList = mutableListOf<ProductModel>()

    class ProductViewHolder(private var binding: ProductsCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel : ProductModel) {
            binding.product = productModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(ProductsCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productsList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setList(list: List<ProductModel>?){
        list?.let {
            this.productsList = list as MutableList<ProductModel>
            notifyDataSetChanged()
        }
    }
}