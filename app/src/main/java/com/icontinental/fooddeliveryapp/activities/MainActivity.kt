package com.icontinental.fooddeliveryapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.icontinental.fooddeliveryapp.componentes.categoria.AdapterCategorias
import com.icontinental.fooddeliveryapp.model.Categoria
import com.icontinental.fooddeliveryapp.R
import com.icontinental.fooddeliveryapp.componentes.productos.AdapterProductos
import com.icontinental.fooddeliveryapp.model.Producto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.icontinental.fooddeliveryapp.model.Usuario

class MainActivity : AppCompatActivity() {

    lateinit var recyclerViewCategorias: RecyclerView

    lateinit var recyclerViewProductos: RecyclerView

    lateinit var buttonCerrarSesion: AppCompatButton

    lateinit var linearlayoutUbicacion: LinearLayoutCompat

    lateinit var usuario: Usuario

    lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Firebase.firestore

        auth = Firebase.auth

        obtenerUsuario()

//        guardarUsuarioEnMemoria(usuario)

        val productos = obtenerProductos()

        val categorias = obtenerCategorias()

        recyclerViewCategorias = findViewById(R.id.recyclerviewCategorias)

        recyclerViewProductos = findViewById(R.id.recyclerviewMasPopulares)

//        buttonAgregarNuevoProducto = findViewById(R.id.buttonAgregarNuevoProducto)

        buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion)

        linearlayoutUbicacion = findViewById(R.id.linearlayoutUbicacion)

//        buttonAgregarNuevoProducto.setOnClickListener {
//            agregarNuevoProducto("Pizza Americana", "Pizzas", 20.0, 15.0, "ic_pizza_americana")
//        }

        buttonCerrarSesion.setOnClickListener {
            auth.signOut()
            finish()
        }

        linearlayoutUbicacion.setOnClickListener {

            val intent = Intent(this, UbicacionActivity::class.java)
            Log.d("LATITUD MAIN ACTIVITY", "latitud: ${usuario?.latitud}")
            Log.d("LONGITUD MAIN ACTIVITY", "longitud: ${usuario?.longitud}")
            intent.putExtra("latitud", usuario?.latitud ?: "0.0")
            intent.putExtra("longitud", usuario?.longitud ?: "0.0")
            intent.putExtra("nombres", usuario?.nombres ?: "")
            intent.putExtra("apellidos", usuario?.apellidos ?: "")
            intent.putExtra("direccion", usuario?.direccion ?: "")

            startActivity(intent)
        }

        resources.getIdentifier("ic_pizza", "drawable", packageName)

        val adapter = AdapterCategorias(categorias, this)

        val adapterProductos = AdapterProductos(productos, this)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManagerProductos = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerViewCategorias.layoutManager = linearLayoutManager
        recyclerViewCategorias.adapter = adapter

        recyclerViewProductos.layoutManager = linearLayoutManagerProductos
        recyclerViewProductos.adapter = adapterProductos
    }

    fun obtenerUsuario() {

        db.collection("usuarios")
            .whereEqualTo("email", "johan.instructor@icontinental.pe")
            .get()
            .addOnSuccessListener { documents ->
                usuario = documents.first().toObject(Usuario::class.java)
                Log.d("OBTENER USUARIO", "${usuario?.nombres} => ${usuario?.latitud} ${usuario?.longitud}")
            }
            .addOnFailureListener { exception ->
                Log.e("OBTENER USUARIO", "Error getting documents: ", exception)
            }
    }

    fun guardarUsuarioEnMemoria(usuario: Usuario?) {

        val sharedPreference = getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        Log.d("USUARIO LATITUD", usuario?.latitud ?: "1.1")
        Log.d("USUARIO LONGITUD", usuario?.longitud.toString())
        editor.putString("latitud",usuario?.latitud ?: "1.1")
        editor.putString("longitud",usuario?.longitud ?: "1.1")
        editor.apply()
    }

    fun obtenerProductos(): ArrayList<Producto> {
        var productos = arrayListOf<Producto>()

        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    Log.d("OBTENER DOCUMENTO", "${document.id} => ${document.data}")

                    productos.add(document.toObject(Producto::class.java))
                }
                recyclerViewProductos.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ERROR EN LEER DATOS", "Error getting documents.", exception)
            }
        return productos
    }

    fun obtenerCategorias(): ArrayList<Categoria> {
        var categorias = arrayListOf<Categoria>()

        db.collection("categorias")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    Log.d("OBTENER DOCUMENTO", "${document.id} => ${document.data}")

                    categorias.add(document.toObject(Categoria::class.java))
                }
                recyclerViewCategorias.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ERROR EN LEER DATOS", "Error getting documents.", exception)
            }
        return categorias
    }

    fun agregarNuevoProducto(
        nombre: String,
        categoria: String,
        precioRegular: Double,
        precioDescuento: Double,
        imagen: String
    ) {

        val producto = hashMapOf(
            "nombre" to nombre,
            "categoria" to categoria,
            "precioRegular" to precioRegular,
            "precioDescuento" to precioDescuento,
            "imagen" to imagen
        )

        db.collection("productos")
            .add(producto)
            .addOnSuccessListener { documentReference ->
                Log.d("CREACION DOCUMENTO", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "Se agrego nuevo producto", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "DocumentSnapshot added with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("ERROR EN CREACION", "Error adding document", e)
                Toast.makeText(this, "Hubo un error al crear producto", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Error adding document: ${e}", Toast.LENGTH_SHORT).show()
            }
    }
}