package com.example.coachrythmo.presentation.map

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.coachrythmo.ui.theme.CRPrimaryRed
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapPickerScreen(
    navController: NavController,
    onLocationPicked: (Double, Double) -> Unit
) {
    val context = LocalContext.current
    var selectedPoint by remember { mutableStateOf<GeoPoint?>(null) }
    var marker by remember { mutableStateOf<Marker?>(null) }

    Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choisir un lieu") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            if (selectedPoint != null) {
                FloatingActionButton(
                    onClick = {
                        selectedPoint?.let {
                            onLocationPicked(it.latitude, it.longitude)
                            navController.popBackStack()
                        }
                    },
                    containerColor = CRPrimaryRed
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Confirmer", tint = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AndroidView(
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(15.0)
                        controller.setCenter(GeoPoint(45.5088, -73.5878)) // Montréal par défaut

                        val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
                            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                                selectedPoint = p
                                marker?.let { overlays.remove(it) }
                                val newMarker = Marker(this@apply).apply {
                                    position = p
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    title = "Lieu sélectionné"
                                }
                                marker = newMarker
                                overlays.add(newMarker)
                                invalidate()
                                return true
                            }

                            override fun longPressHelper(p: GeoPoint): Boolean = false
                        })
                        overlays.add(eventsOverlay)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            if (selectedPoint == null) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp),
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Appuie sur la carte pour choisir un lieu",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}