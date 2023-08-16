package com.icontinental.fooddeliveryapp.componentes.categoria

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.icontinental.fooddeliveryapp.R

class ViewHolderCategorias(card: View): RecyclerView.ViewHolder(card) {
    val cardFondo: ConstraintLayout
    val circuloFondoImagen: LinearLayoutCompat
    val categoriaImagen: ImageView
    val nombreCategoria: TextView

    init {
        cardFondo = card.findViewById(R.id.card_categoria)
        circuloFondoImagen = card.findViewById(R.id.linearlayoutCirculoFondo)
        categoriaImagen = card.findViewById(R.id.imageViewCategoria)
        nombreCategoria = card.findViewById(R.id.textViewNombreCategoria)
    }
}