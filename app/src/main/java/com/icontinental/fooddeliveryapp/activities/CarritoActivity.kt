package com.icontinental.fooddeliveryapp.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.icontinental.fooddeliveryapp.R
import com.icontinental.fooddeliveryapp.model.Producto

class CarritoActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var productos: ArrayList<Producto>
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        gson = Gson()
        sharedPreferences = getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)

        obtenerProductosMemoria()
    }

    fun obtenerProductosMemoria(){
        val productosJson = sharedPreferences.getString("carrito", null)

        if (productosJson != null) {
            val type = object : TypeToken<ArrayList<Producto>>() {}.type
            productos = gson.fromJson(productosJson, type)
            Log.d("PRODUCTO DE CARRRITO DE COMPRAS", "PRODUCTOS: $productos")
            // [productoJSON, productoJSON, productoJSON]
        } else {
            productos = arrayListOf<Producto>()
            // []
            Log.d("PRODUCTO DE CARRRITO DE COMPRAS VACIO", "PRODUCTOS: $productos")
            Log.d("PRODUCTO DE CARRRITO DE COMPRAS VACIO", "PRODUCTOS: VACIO")
        }
    }
}