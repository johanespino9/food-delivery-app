package com.icontinental.fooddeliveryapp.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.icontinental.fooddeliveryapp.R
import com.icontinental.fooddeliveryapp.model.Producto

class DetalleProducto : AppCompatActivity() {

    lateinit var textviewNombre: TextView
    lateinit var textviewCategoria: TextView
    lateinit var textviewPrecioDescuento: TextView
    lateinit var textviewPrecioRegular: TextView
    lateinit var imageviewProductoDetalle: ImageView
    lateinit var buttonAgregarCarrito: AppCompatButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var producto: Producto
    lateinit var productosMemoria: ArrayList<Producto>
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)
        gson = Gson()
        sharedPreferences =  getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)

        textviewNombre = findViewById(R.id.textviewNombreValor)
        textviewCategoria = findViewById(R.id.textviewCategoriaValor)
        textviewPrecioDescuento = findViewById(R.id.textviewPrecioDescuentoValor)
        textviewPrecioRegular = findViewById(R.id.textviewPrecioRegularValor)
        imageviewProductoDetalle = findViewById(R.id.imageviewProductoDetalle)
        buttonAgregarCarrito = findViewById(R.id.buttonAgregarCarrito)

        val extras = intent.extras
        val nombre = extras?.getString("nombre")
        val categoria = extras?.getString("categoria")
        val imagen = extras?.getString("imagen")
        val precioDescuento = extras?.getDouble("precioDescuento").toString()
        val precioRegular = extras?.getDouble("precioRegular").toString()

        textviewNombre.text = nombre
        textviewCategoria.text = categoria
        textviewPrecioDescuento.text = "S/ $precioDescuento"
        textviewPrecioRegular.text = "S/ $precioRegular"

        val id = this.resources.getIdentifier(imagen, "drawable", this.packageName)
        imageviewProductoDetalle.setImageResource(id)

        buttonAgregarCarrito.setOnClickListener {
            setearProducto(imagen ?: "", nombre ?: "", categoria ?: "", precioRegular.toDouble(), precioDescuento.toDouble())
            obtenerProductosMemoria()
            agregarProductoCarrito()
        }
    }

    fun setearProducto(imagen: String, nombre: String, categoria: String, precioRegular: Double, precioDescuento: Double){
        producto = Producto(imagen, nombre, categoria, precioRegular, precioDescuento)
    }

    fun obtenerProductosMemoria(){
        val productosJson = sharedPreferences.getString("carrito", null)

        if (productosJson != null) {
            val type = object : TypeToken<ArrayList<Producto>>() {}.type
            productosMemoria = gson.fromJson(productosJson, type)
            // [productoJSON, productoJSON, productoJSON]
        } else {
            productosMemoria = arrayListOf<Producto>()
            // []
        }
    }

    fun agregarProductoCarrito(){
        productosMemoria.add(producto)

        val productosJson = gson.toJson(productosMemoria)

        sharedPreferences.edit()
            .putString("carrito", productosJson)
            .apply()
        Toast.makeText(this, "Se añadio un nuevo producto al carrito ${producto.nombre}", Toast.LENGTH_LONG).show()
    }
}