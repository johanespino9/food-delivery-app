package com.icontinental.fooddeliveryapp.activities

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.icontinental.fooddeliveryapp.R
import com.icontinental.fooddeliveryapp.model.Usuario
import kotlinx.coroutines.SupervisorJob


class UbicacionActivity : AppCompatActivity(), OnMapReadyCallback, PlaceSelectionListener {

    lateinit var sharedPreference: SharedPreferences
    lateinit var textviewNombres: TextView
    lateinit var textviewApellidos: TextView
    lateinit var textviewDireccion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)

        sharedPreference =  getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)

        textviewNombres = findViewById(R.id.textviewNombresValor)
        textviewApellidos = findViewById(R.id.textviewApellidosValor)
        textviewDireccion = findViewById(R.id.textviewDireccionValor)

        val extras = intent.extras
        val latitud = extras?.getString("latitud")
        val longitud = extras?.getString("longitud")
        val nombres = extras?.getString("nombres")
        val apellidos = extras?.getString("apellidos")
        val direccion = extras?.getString("direccion")

        textviewNombres.text = obtenerNombres()
        textviewApellidos.text = obtenerApellidos()
        textviewDireccion.text = obtenerDireccion()

        Log.d("LATITUD UBICACION ACTIVITY", "latitud: $latitud")
        Log.d("LONGITUD UBICACION ACTIVITY", "longitud: $longitud")
        Log.d("UBICACION ACTIVITY", "nombres: $nombres")
        Log.d("UBICACION ACTIVITY", "apellidos: $apellidos")
        Log.d("DIRECCION ACTIVITY", "direccion: $direccion")

        Places.initialize(applicationContext, "AIzaSyBBjz_DGivwZ4_UYJL_hmInCbyPg4KSkko")

        val mapFragment = SupportMapFragment.newInstance()
        val latlng = LatLng(latitud?.toDouble() ?: 0.0, longitud?.toDouble() ?: 0.0)
        mapFragment.getMapAsync {


            it.moveCamera(CameraUpdateFactory.newLatLngZoom(obtenerLatLng(), 17.0f))

            it.addMarker(
                MarkerOptions()
                    .position(obtenerLatLng())
                    .title("Mi ubicacion actual")
            )
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_map, mapFragment)
            .commit()
    }

    fun obtenerNombres(): String? {
        val nombresValue = sharedPreference.getString("nombres","").orEmpty()
        Log.d("MAPAS", "Latitud Value: $nombresValue")
        val nombres = nombresValue

        Log.d("MAPAS", "Nombres: $nombres")

        return nombres
    }

    fun obtenerApellidos(): String? {
        val apellidosValue = sharedPreference.getString("apellidos","").orEmpty()
        Log.d("MAPAS", "Apellidos Value: $apellidosValue")
        val apellidos = apellidosValue

        Log.d("MAPAS", "Apellidos: $apellidos")

        return apellidos
    }

    fun obtenerDireccion(): String? {
        val direccionValue = sharedPreference.getString("direccion","").orEmpty()
        Log.d("MAPAS", "direccion Value: $direccionValue")
        val direccion = direccionValue

        Log.d("MAPAS", "direccion: $direccion")

        return direccion
    }

    fun obtenerLatitud(): Double? {
        val latitudValue = sharedPreference.getString("latitud","defaultName").orEmpty()
        Log.d("MAPAS", "Latitud Value: $latitudValue")
        val latitud = latitudValue?.toDouble()

        Log.d("MAPAS", "Latitud: $latitud")

        return latitud
    }

    fun obtenerLongitud(): Double? {
        val longitudValue = sharedPreference.getString("longitud","defaultName").orEmpty()
        Log.d("MAPAS", "LongitudValue: $longitudValue")
        val longitud = longitudValue?.toDouble()
        Log.d("MAPAS", "Longitud: $longitud")
        return longitud
    }

    fun obtenerLatLng(): LatLng {

        val lat = obtenerLatitud()
        val lng = obtenerLongitud()

        Log.d("MAPAS", "Latitud: $lat")
        Log.d("MAPAS", "Longitud: $lng")

        return LatLng(lat ?: 0.0, lng ?: 0.0)
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

    override fun onError(p0: Status) {
        TODO("Not yet implemented")
    }

    override fun onPlaceSelected(p0: Place) {
        TODO("Not yet implemented")
    }
}