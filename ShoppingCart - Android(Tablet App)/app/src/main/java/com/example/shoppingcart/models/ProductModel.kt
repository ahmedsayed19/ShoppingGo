package com.example.shoppingcart.models

import com.squareup.moshi.Json

data class ProductModel(
    @Json(name = "id") val id: Int,
    @Json(name = "title") var productName: String,
    @Json(name = "image") val productImage: String,
    @Json(name = "price") var productPrice: String,
    var productUnit: Int = 1
){
    fun increaseThePrice(p1: String , p2: String) : String{
        val sum = p1.toDouble() + p2.toDouble()
        return String.format("%.1f", sum)
    }

    override fun equals(other: Any?): Boolean {
        other as ProductModel
        return id == other.id && productImage == other.productImage
    }
}