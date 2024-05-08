package no.uio.ifi.in2000.team37.badeturisten

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import no.uio.ifi.in2000.team37.badeturisten.ui.components.Screens
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BeachProfile
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BottomNavigationBar
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.theme.BadeturistenTheme

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            var locationPermissionsGranted by remember { mutableStateOf(checkLocationPermission()) }
            var shouldShowPermissionRationale by remember {
                mutableStateOf(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
            }
            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }
            var currentPermissionsStatus by remember {
                mutableStateOf(
                    decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
                )
            }

            val locationPermissionsLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                locationPermissionsGranted =
                    permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

                if (!locationPermissionsGranted) {
                    shouldShowPermissionRationale =
                        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                shouldDirectUserToApplicationSettings =
                    !shouldShowPermissionRationale && !locationPermissionsGranted
                currentPermissionsStatus = decideCurrentPermissionStatus(
                    locationPermissionsGranted,
                    shouldShowPermissionRationale
                )
            }

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START && !locationPermissionsGranted && !shouldShowPermissionRationale) {
                        locationPermissionsLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            BadeturistenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()

                }
            }
        }
    }

    private fun getLastLocation() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    println("Location: ${location.latitude}, ${location.longitude}")
                } else {
                    println("No location available.")
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            println("Permission was denied")
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun Activity.openApplicationSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also {
            startActivity(it)
        }
    }

    private fun decideCurrentPermissionStatus(granted: Boolean, rationale: Boolean): String {
        return when {
            granted -> "Granted"
            rationale -> "Rejected"
            else -> "Denied"
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(
                route = "beachProfile/{beachName}",
                arguments = listOf(navArgument("beachName") { type = NavType.StringType })
            ) { backStackEntry ->
                val beachName = backStackEntry.arguments?.getString("beachName")
                BeachProfile(navController = navController, beachName = beachName)
            }

            composable(route = "homeScreen") {
                HomeScreen(navController = navController)
            }

            composable(route = "favoritesScreen") {
                FavouritesScreen(navController = navController)
            }
            composable(route = "searchScreen") {
                SearchScreen(navController = navController)
            }
        }
    }
}