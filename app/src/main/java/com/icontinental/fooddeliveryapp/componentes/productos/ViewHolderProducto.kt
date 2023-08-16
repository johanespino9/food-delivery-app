package com.icontinental.fooddeliveryapp.componentes.productos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.icontinental.fooddeliveryapp.R

class ViewHolderProducto(card: View) :RecyclerView.ViewHolder(card) {
    val productoImagen: ImageView
    val productoNombre: TextView
    val productoCategoria: TextView
    val productoPrecioDescuento: TextView
    val productoPrecioRegular: TextView

    init {
        productoImagen = card.findViewById(R.id.imageViewProducto)
        productoNombre = card.findViewById(R.id.textviewNombreProducto)
        productoCategoria = card.findViewById(R.id.textViewCategoriaProducto)
        productoPrecioDescuento = card.findViewById(R.id.textviewPrecioDescuento)
        productoPrecioRegular = card.findViewById(R.id.textviewPrecioRegular)
    }
}