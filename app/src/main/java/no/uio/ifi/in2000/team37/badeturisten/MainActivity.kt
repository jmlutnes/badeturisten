package no.uio.ifi.in2000.team37.badeturisten

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleEventObserver
import no.uio.ifi.in2000.team37.badeturisten.ui.components.Screens
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BeachProfile
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.theme.BadeturistenTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var locationPermissionsGranted by remember { mutableStateOf(areLocationPermissionsAlreadyGranted()) }
            var shouldShowPermissionRationale by remember {
                mutableStateOf(
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }

            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }

            var currentPermissionsStatus by remember {
                mutableStateOf(decideCurrentPermissionStatus(locationPermissionsGranted, shouldShowPermissionRationale))
            }

            val locationPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            val locationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                        acc && isPermissionGranted
                    }

                    if (!locationPermissionsGranted) {
                        shouldShowPermissionRationale =
                            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                    shouldDirectUserToApplicationSettings = !shouldShowPermissionRationale && !locationPermissionsGranted
                    currentPermissionsStatus = decideCurrentPermissionStatus(locationPermissionsGranted, shouldShowPermissionRationale)
                })

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START &&
                        !locationPermissionsGranted &&
                        !shouldShowPermissionRationale) {
                        locationPermissionLauncher.launch(locationPermissions)
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
            )

            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }
            BadeturistenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }) { contentPadding ->
                        Column(modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(modifier = Modifier
                                .padding(contentPadding)
                                .fillMaxWidth(),
                                text = "Location Permissions",
                                textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(modifier = Modifier
                                .padding(contentPadding)
                                .fillMaxWidth(),
                                text = "Current Permission Status: $currentPermissionsStatus",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (shouldShowPermissionRationale) {
                            LaunchedEffect(Unit) {
                                scope.launch {
                                    val userAction = snackbarHostState.showSnackbar(
                                        message ="Please authorize location permissions",
                                        actionLabel = "Approve",
                                        duration = SnackbarDuration.Indefinite,
                                        withDismissAction = true
                                    )
                                    when (userAction) {
                                        SnackbarResult.ActionPerformed -> {
                                            shouldShowPermissionRationale = false
                                            locationPermissionLauncher.launch(locationPermissions)
                                        }
                                        SnackbarResult.Dismissed -> {
                                            shouldShowPermissionRationale = false
                                        }
                                    }
                                }
                            }
                        }
                        if (shouldDirectUserToApplicationSettings) {
                            openApplicationSettings()
                        }
                    }
                    BottomNavigationBar()
                }
            }
        }
    }
}

private fun Context.areLocationPermissionsAlreadyGranted(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

private fun Activity.openApplicationSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).also {
        startActivity(it)
    }
}

private fun decideCurrentPermissionStatus(locationPermissionsGranted: Boolean,
                                          shouldShowPermissionRationale: Boolean): String {
    return if (locationPermissionsGranted) "Granted"
    else if (shouldShowPermissionRationale) "Rejected"
    else "Denied"
}

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Favourite",
                icon = Icons.Filled.Favorite,
                route = Screens.Favorite.route
            ),
            BottomNavigationItem(
                label = "Search",
                icon = Icons.Filled.Search,
                route = Screens.Search.route
            ),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
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
