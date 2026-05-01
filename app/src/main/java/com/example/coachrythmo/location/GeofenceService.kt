package com.example.coachrythmo.location

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.coachrythmo.MainActivity
import com.example.coachrythmo.R
import com.example.coachrythmo.data.source.AppDatabase
import com.google.android.gms.location.*
import kotlinx.coroutines.*

class GeofenceService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val notifiedRoutines = mutableSetOf<String>()


    companion object {
        const val CHANNEL_ID = "geofence_channel"
        const val NOTIF_SERVICE_ID = 1
        const val RADIUS_METERS = 200f
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationChannel()
        startForeground(NOTIF_SERVICE_ID, buildServiceNotification())
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            60_000L // toutes les 60 secondes
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                serviceScope.launch {
                    checkRoutines(location)
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private suspend fun checkRoutines(userLocation: Location) {
        val db = AppDatabase.getInstance(applicationContext)
        val routines = db.routineDao().getAllNow()

        routines.forEach { routine ->
            val lat = routine.latitude ?: return@forEach
            val lng = routine.longitude ?: return@forEach

            val routineLocation = Location("").apply {
                latitude = lat
                longitude = lng
            }

            val distance = userLocation.distanceTo(routineLocation)
            android.util.Log.d("GEOFENCE", "Distance à ${routine.name}: ${distance.toInt()}m")
            if (distance <= RADIUS_METERS && !notifiedRoutines.contains(routine.name)) {
                notifiedRoutines.add(routine.name)
                android.util.Log.d("GEOFENCE", "DANS LA ZONE - déclenchement pour ${routine.name}")
                sendRoutineNotification(routine.name)
            } else if (distance > RADIUS_METERS) {
                notifiedRoutines.remove(routine.name) // reset quand on quitte la zone
            }
        }
    }

    private fun sendRoutineNotification(routineName: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("C'est l'heure de t'entraîner !")
            .setContentText("Tu es près de ton lieu pour : $routineName")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(routineName.hashCode(), notification)
    }

    private fun buildServiceNotification() =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("CoachRythmo actif")
            .setContentText("Surveillance de ta zone d'entraînement...")
            .build()

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Géofencing CoachRythmo",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}