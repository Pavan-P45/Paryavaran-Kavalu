package com.example.paryavarankavalu

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ReportWasteActivity : AppCompatActivity() {

    private lateinit var imageWaste: ImageView

    private var imageUri: Uri? = null

    private var latitude = 0.0
    private var longitude = 0.0

    private val imagePicker =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                imageUri = result.data?.data

                imageWaste.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_report_waste)

        imageWaste = findViewById(R.id.imageWaste)

        val btnSelectImage =
            findViewById<Button>(R.id.btnSelectImage)

        val btnSubmit =
            findViewById<Button>(R.id.btnSubmitReport)

        val etDescription =
            findViewById<EditText>(R.id.etDescription)

        getCurrentLocation()

        btnSelectImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)

            intent.type = "image/*"

            imagePicker.launch(intent)
        }

        btnSubmit.setOnClickListener {

            if (imageUri == null) {

                Toast.makeText(
                    this,
                    "Select Image",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val storageRef =
                FirebaseStorage.getInstance()
                    .reference
                    .child(
                        "waste_images/" +
                                UUID.randomUUID().toString()
                    )

            storageRef.putFile(imageUri!!)
                .addOnSuccessListener {

                    storageRef.downloadUrl
                        .addOnSuccessListener { uri ->

                            val data = hashMapOf(
                                "description" to
                                        etDescription.text.toString(),

                                "imageUrl" to
                                        uri.toString(),

                                "latitude" to
                                        latitude,

                                "longitude" to
                                        longitude,

                                "status" to
                                        "Pending",

                                "timestamp" to
                                        System.currentTimeMillis()
                            )

                            FirebaseFirestore.getInstance()
                                .collection("reports")
                                .add(data)
                                .addOnSuccessListener {

                                    Toast.makeText(
                                        this,
                                        "Report Submitted Successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    finish()
                                }
                        }
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )

            return
        }

        val fusedLocationClient =
            LocationServices
                .getFusedLocationProviderClient(this)

        val locationRequest =
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000
            ).build()

        val locationCallback =
            object : LocationCallback() {

                override fun onLocationResult(
                    locationResult: LocationResult
                ) {

                    val location =
                        locationResult.lastLocation

                    if (location != null) {

                        latitude = location.latitude
                        longitude = location.longitude
                    }

                    fusedLocationClient
                        .removeLocationUpdates(this)
                }
            }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            mainLooper
        )
    }
}