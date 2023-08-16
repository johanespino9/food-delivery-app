package com.icontinental.fooddeliveryapp.componentes.productos

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.icontinental.fooddeliveryapp.R
import com.icontinental.fooddeliveryapp.model.Producto
import kotlin.coroutines.coroutineContext

class AdapterProductos(val lista: List<Producto>, val context: AppCompatActivity): RecyclerView.Adapter<ViewHolderProducto>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProducto {
        val card = LayoutInflater.from(parent.context).inflate(R.layout.card_producto, parent, false)

        return ViewHolderProducto(card)
    }

    override fun getItemCount(): Int {
        Log.d("TAMANIO DE LA LISTA DE PRODUCTOS:", "${lista.size}")
       return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolderProducto, position: Int) {
        // unir la data con las vistas o componentes

        val producto = lista[position]
        Log.d("PRODUCTOS", "${producto.nombre}")
        holder.productoNombre.text = producto.nombre
        holder.productoCategoria.text = producto.categoria
        holder.productoPrecioDescuento.text = producto.precioDescuento.toString()
        holder.productoPrecioRegular.text = producto.precioRegular.toString()
        val id = context.resources.getIdentifier(producto.imagen, "drawable", context.packageName)
        holder.productoImagen.setImageResource(id)
    }
}