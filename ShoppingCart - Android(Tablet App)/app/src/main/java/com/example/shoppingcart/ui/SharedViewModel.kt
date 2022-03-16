package com.example.shoppingcart.ui

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcart.models.ProductModel
import com.example.shoppingcart.networkAndConnections.BluetoothImpl
import com.example.shoppingcart.networkAndConnections.BluetoothThread
import com.example.shoppingcart.networkAndConnections.ProductsApi
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class SharedViewModel : ViewModel(){
    // The internal MutableLiveData that stores the list of the products.
    private val _products = MutableLiveData<List<ProductModel>>()
    // The external immutable LiveData for the List of Products.
    val products : LiveData<List<ProductModel>> = _products
    // temporary list to assign the _products of type mutableLiveData.
    val list = mutableListOf<ProductModel>()

    // the total price of the products.
    private val _totalPrice = MutableLiveData(0.0)
    // The external immutable LiveData for the total price.
    val price : LiveData<Double> = _totalPrice

   // Instance of our bluetooth class that hold all bluetooth function.
    var bluetooth: BluetoothThread? = null

    // if i want to get one product at a time.
    suspend fun getProduct() : ProductModel? {
        var product: ProductModel? = null
        try {
            product = ProductsApi.retrofitServices.getProduct()
        } catch (e: Exception) {
            println(e)
        }
        return product
    }

//    suspend fun getProduct2() : ProductModel? {
//        var product: ProductModel? = null
//        try {
//            product = ProductsApi.retrofitServices.getProduct2()
//        } catch (e: Exception) {
//            println(e)
//        }
//        return product
//    }

    fun checkIfProductExist(product: ProductModel, list: MutableList<ProductModel>){
        var isExist = false
        if (list.isEmpty()){
            list.add(product)
        }else{
            val iterator = list.iterator()
            while (iterator.hasNext()){
                val item = iterator.next()
                if (item == product){
                    isExist = true
                    item.productUnit++
                    item.productPrice = item.increaseThePrice(item.productPrice,product.productPrice)
                }
            }
            if (!isExist)
                list.add(product)
        }
        _products.postValue(list)
        getTotalPrice()
    }

    // Calculate the total price for the list of products and round the total to 1 decimal numbers.
    private fun getTotalPrice(){
        val total = ArrayList<Double>()
        _products.value?.forEach { product ->
            if (product.productName.length > 40)
                product.productName = product.productName.substring(0,39)
            total.add(product.productPrice.toDouble())
        }
       _totalPrice.value = String.format("%.1f", total.sum()).toDouble()
    }

    // generate Qrcode with all information needed.
     fun generateQR(imageView: ImageView){
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(price.toString(),BarcodeFormat.QR_CODE, 200,200)
            imageView.setImageBitmap(bitmap)
        }catch (e: Exception){
            println(e)
        }
     }
}