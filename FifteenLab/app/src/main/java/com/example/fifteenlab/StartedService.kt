package com.example.fifteenlab

import android.annotation.SuppressLint
import android.app.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.google.android.gms.common.internal.Constants
import com.google.android.gms.location.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

@SuppressLint("MissingPermission")
class StartedService : Service() {
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var notificationIntent: Intent
    private var speedList:ArrayList<Float> = ArrayList()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ExceptionLog","onStartCommand")
        speedList = ArrayList()
        notificationIntent = Intent(this, MainActivity::class.java)
        startForeground(1, getNotification("text"))
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=5000
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null){
                        speedList.add(location.speed)
                        updateNotification("Средняя скорость: " + (speedList.sum()/speedList.size).toString() + "м/c")
                    }
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
        return START_STICKY
    }
    private fun getNotification(text: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        return Notification.Builder(this, MainActivity.CHANNEL_ID)
            .setContentTitle(text)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()
    }
    fun updateNotification(text: String) {
        val notification: Notification = getNotification(text)

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, notification)
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}