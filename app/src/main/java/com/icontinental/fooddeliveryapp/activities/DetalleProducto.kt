package com.icontinental.fooddeliveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.icontinental.fooddeliveryapp.R
import com.icontinental.fooddeliveryapp.model.Categoria

class DetalleProducto : AppCompatActivity() {

    lateinit var textviewNombre: TextView
    lateinit var textviewCategoria: TextView
    lateinit var textviewPrecioDescuento: TextView
    lateinit var textviewPrecioRegular: TextView
    lateinit var imageviewProductoDetalle: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        textviewNombre = findViewById(R.id.textviewNombreValor)
        textviewCategoria = findViewById(R.id.textviewCategoriaValor)
        textviewPrecioDescuento = findViewById(R.id.textviewPrecioDescuentoValor)
        textviewPrecioRegular = findViewById(R.id.textviewPrecioRegularValor)
        imageviewProductoDetalle = findViewById(R.id.imageviewProductoDetalle)

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
    }
}