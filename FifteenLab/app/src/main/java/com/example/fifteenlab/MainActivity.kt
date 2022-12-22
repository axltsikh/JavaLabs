package com.example.fifteenlab

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isPermissionsGranted()
        Filler()
        createNotificationChannel()
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

    }
    fun Filler(){
        val startButton: Button =findViewById(R.id.StartButton)
        val stopButton:Button=findViewById(R.id.StopButton)
        val imageButton:Button=findViewById(R.id.ImageButton)
        startButton.setOnClickListener(View.OnClickListener {
            val service = Intent(this, StartedService::class.java)
            ContextCompat.startForegroundService(this, service)
        })
        stopButton.setOnClickListener(View.OnClickListener {
            val service = Intent(this, StartedService::class.java)
            stopService(service)
        })
        imageButton.setOnClickListener(View.OnClickListener {
            val text:EditText=findViewById(R.id.ImageUrl)
            val intent = Intent(this, ImageIntentService::class.java)
            startService(intent.putExtra("image",text.text.toString()))
        })
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
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setSound(null, null)
                enableLights(false)
                enableVibration(false)

            }

            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "exampleServiceChannel"
    }
}