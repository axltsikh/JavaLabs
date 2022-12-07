package com.example.fourteenlab

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.String
import java.util.*


@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    private val TAG: String ="ExceptionLong"
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLocation:TextView
    private lateinit var source: Spinner
    private lateinit var rate:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isPermissionsGranted()

        source=findViewById(R.id.sourceSpinner)
        rate=findViewById(R.id.rateSpinner)
        currentLocation=findViewById(R.id.CurrentLocation)
        val s: kotlin.String = this.filesDir.toString()
        val file:File= File(s+"Log.txt")
        if(!file.exists())
            file.createNewFile()
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        val startButton:Button=findViewById(R.id.startLocation)
        val endButton:Button=findViewById(R.id.stopLocation)
        startButton.setOnClickListener {
            GlobalScope.launch {
                locationRequest = LocationRequest.create()
                when(source.selectedItemId.toInt()){
                    0->locationRequest.priority=LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                    1->locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                when(rate.selectedItemId.toInt()){
                    0->locationRequest.interval = (5000).toLong()
                    1->locationRequest.interval = (10000).toLong()
                    2->locationRequest.interval = (20000).toLong()
                    3->locationRequest.interval = (30000).toLong()
                    4->locationRequest.interval = (60000).toLong()
                }
                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        if (locationResult == null) {
                            return
                        }
                        for (location in locationResult.locations) {
                            if (location != null) {
                                currentLocation.text=""
                                currentLocation.append("Долгота: " + location.longitude.toString() + "\n")
                                currentLocation.append("Широта: " + location.latitude.toString() + "\n")
                                currentLocation.append("Скорость (метры в секунду): " + location.speedAccuracyMetersPerSecond.toString() + "\n")
                                currentLocation.append("Время: " + Date(location.time) + "\n")
                                currentLocation.append("Высота над уровнем моря в метрах: " + location.altitude + "\n")
                                file.appendText("Долгота: " + location.longitude.toString() + "\n")
                                file.appendText("Широта: " + location.latitude.toString() + "\n")
                                file.appendText("Скорость (метры в секунду): " + location.speedAccuracyMetersPerSecond.toString() + "\n")
                                file.appendText("Время: " + Date(location.time) + "\n")
                                file.appendText("Высота над уровнем моря в метрах: " + location.altitude + "\n")
                            }
                        }
                    }
                }
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
            }
        }
        endButton.setOnClickListener {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            Toast.makeText(this,"Stopped successfully!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun isPermissionsGranted():Boolean{
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                1
            )
            false
        } else {
            true
        }
    }
}