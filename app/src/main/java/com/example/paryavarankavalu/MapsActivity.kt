package com.example.paryavarankavalu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        FirebaseFirestore.getInstance()
            .collection("reports")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {

                    val latitude =
                        document.getDouble("latitude") ?: 0.0

                    val longitude =
                        document.getDouble("longitude") ?: 0.0

                    val description =
                        document.getString("description") ?: "Waste Report"

                    val location = LatLng(latitude, longitude)

                    mMap.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title(description)
                    )

                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(location, 12f)
                    )
                }
            }
    }
}