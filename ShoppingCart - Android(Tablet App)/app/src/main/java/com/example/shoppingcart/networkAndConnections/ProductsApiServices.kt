package com.example.shoppingcart.networkAndConnections

import com.example.shoppingcart.models.ProductModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://fakestoreapi.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ProductsApiServices{
    @GET("products/1")
    suspend fun getProduct() : ProductModel
    @GET("products/2")
    suspend fun getProduct2() : ProductModel
}

object ProductsApi{
    val retrofitServices : ProductsApiServices by lazy {
        retrofit.create(ProductsApiServices ::class.java)
    }
}