package no.uio.ifi.in2000.team37.badeturisten.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.verticalNegativePadding
import kotlinx.coroutines.delay
import no.uio.ifi.in2000.team37.badeturisten.R
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BeachCard

@Composable
fun rememberWarning(areActiveAlerts: Boolean): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "warning",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = if (areActiveAlerts) {
                    SolidColor(Color.Red)
                } else {
                    SolidColor(Color.White)
                },
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
            ) {
                moveTo(20.042f, 20.542f)
                close()
                moveTo(4.292f, 34.75f)
                quadToRelative(-0.75f, 0f, -1.125f, -0.646f)
                reflectiveQuadToRelative(0f, -1.312f)
                lineTo(18.875f, 5.667f)
                quadToRelative(0.375f, -0.625f, 1.125f, -0.625f)
                reflectiveQuadToRelative(1.125f, 0.625f)
                lineToRelative(15.708f, 27.125f)
                quadToRelative(0.375f, 0.666f, 0f, 1.312f)
                reflectiveQuadToRelative(-1.125f, 0.646f)
                close()
                moveToRelative(15.833f, -18.333f)
                quadToRelative(-0.542f, 0f, -0.937f, 0.395f)
                quadToRelative(-0.396f, 0.396f, -0.396f, 0.896f)
                verticalLineTo(24f)
                quadToRelative(0f, 0.583f, 0.396f, 0.958f)
                quadToRelative(0.395f, 0.375f, 0.937f, 0.375f)
                reflectiveQuadToRelative(0.937f, -0.375f)
                quadToRelative(0.396f, -0.375f, 0.396f, -0.958f)
                verticalLineToRelative(-6.292f)
                quadToRelative(0f, -0.5f, -0.396f, -0.896f)
                quadToRelative(-0.395f, -0.395f, -0.937f, -0.395f)
                close()
                moveToRelative(0f, 13.541f)
                quadToRelative(0.542f, 0f, 0.937f, -0.396f)
                quadToRelative(0.396f, -0.395f, 0.396f, -0.937f)
                quadToRelative(0f, -0.583f, -0.396f, -0.958f)
                quadToRelative(-0.395f, -0.375f, -0.937f, -0.375f)
                quadToRelative(-0.583f, 0f, -0.958f, 0.375f)
                reflectiveQuadToRelative(-0.375f, 0.958f)
                quadToRelative(0f, 0.542f, 0.375f, 0.937f)
                quadToRelative(0.375f, 0.396f, 0.958f, 0.396f)
                close()
                moveToRelative(-13.5f, 2.167f)
                horizontalLineToRelative(26.75f)
                lineTo(20f, 9f)
                close()
            }
        }.build()
    }
}

val imageMap = mapOf(
    "clearsky_day" to R.drawable.clearsky_day,
    "clearsky_night" to R.drawable.clearsky_night,
    "clearsky_polartwilight" to R.drawable.clearsky_polartwilight,
    "fair_day" to R.drawable.fair_day,
    "fair_night" to R.drawable.fair_night,
    "fair_polartwilight" to R.drawable.fair_polartwilight,
    "partlycloudy_day" to R.drawable.partlycloudy_day,
    "partlycloudy_night" to R.drawable.partlycloudy_night,
    "partlycloudy_polartwilight" to R.drawable.partlycloudy_polartwilight,
    "cloudy" to R.drawable.cloudy,
    "rainshowers_day" to R.drawable.rainshowers_day,
    "rainshowers_night" to R.drawable.rainshowers_night,
    "rainshowers_polartwilight" to R.drawable.rainshowers_polartwilight,
    "rainshowersandthunder_day" to R.drawable.rainshowersandthunder_day,
    "rainshowersandthunder_night" to R.drawable.rainshowersandthunder_night,
    "rainshowersandthunder_polartwilight" to R.drawable.rainshowersandthunder_polartwilight,
    "sleetshowers_day" to R.drawable.sleetshowers_day,
    "sleetshowers_night" to R.drawable.sleetshowers_night,
    "sleetshowers_polartwilight" to R.drawable.sleetshowers_polartwilight,
    "snowshowers_day" to R.drawable.snowshowers_day,
    "snowshowers_night" to R.drawable.snowshowers_night,
    "snowshowers_polartwilight" to R.drawable.snowshowers_polartwilight,
    "rain" to R.drawable.rain,
    "heavyrain" to R.drawable.heavyrain,
    "heavyrainandthunder" to R.drawable.heavyrainandthunder,
    "sleet" to R.drawable.sleet,
    "snow" to R.drawable.snow,
    "snowandthunder" to R.drawable.snowandthunder,
    "fog" to R.drawable.fog,
    "sleetshowersandthunder_day" to R.drawable.sleetshowersandthunder_day,
    "sleetshowersandthunder_night" to R.drawable.sleetshowersandthunder_night,
    "sleetshowersandthunder_polartwilight" to R.drawable.sleetshowersandthunder_polartwilight,
    "snowshowersandthunder_day" to R.drawable.snowshowersandthunder_day,
    "snowshowersandthunder_night" to R.drawable.snowshowersandthunder_night,
    "snowshowersandthunder_polartwilight" to R.drawable.snowshowersandthunder_polartwilight,
    "rainandthunder" to R.drawable.rainandthunder,
    "sleetandthunder" to R.drawable.sleetandthunder,
    "lightrainshowersandthunder_day" to R.drawable.lightrainshowersandthunder_day,
    "lightrainshowersandthunder_night" to R.drawable.lightrainshowersandthunder_night,
    "lightrainshowersandthunder_polartwilight" to R.drawable.lightrainshowersandthunder_polartwilight,
    "heavyrainshowersandthunder_day" to R.drawable.heavyrainshowersandthunder_day,
    "heavyrainshowersandthunder_night" to R.drawable.heavyrainshowersandthunder_night,
    "heavyrainshowersandthunder_polartwilight" to R.drawable.heavyrainshowersandthunder_polartwilight,
    "lightssleetshowersandthunder_day" to R.drawable.lightssleetshowersandthunder_day,
    "lightssleetshowersandthunder_night" to R.drawable.lightssleetshowersandthunder_night,
    "lightssleetshowersandthunder_polartwilight" to R.drawable.lightssleetshowersandthunder_polartwilight,
    "heavysleetshowersandthunder_day" to R.drawable.heavysleetshowersandthunder_day,
    "heavysleetshowersandthunder_night" to R.drawable.heavysleetshowersandthunder_night,
    "heavysleetshowersandthunder_polartwilight" to R.drawable.heavysleetshowersandthunder_polartwilight,
    "lightssnowshowersandthunder_day" to R.drawable.lightssnowshowersandthunder_day,
    "lightssnowshowersandthunder_night" to R.drawable.lightssnowshowersandthunder_night,
    "lightssnowshowersandthunder_polartwilight" to R.drawable.lightssnowshowersandthunder_polartwilight,
    "heavysnowshowersandthunder_day" to R.drawable.heavysnowshowersandthunder_day,
    "heavysnowshowersandthunder_night" to R.drawable.heavysnowshowersandthunder_night,
    "heavysnowshowersandthunder_polartwilight" to R.drawable.heavysnowshowersandthunder_polartwilight,
    "lightrainandthunder" to R.drawable.lightrainandthunder,
    "lightsleetandthunder" to R.drawable.lightsleetandthunder,
    "heavysleetandthunder" to R.drawable.heavysleetandthunder,
    "lightsnowandthunder" to R.drawable.lightsnowandthunder,
    "heavysnowandthunder" to R.drawable.heavysnowandthunder,
    "lightrainshowers_day" to R.drawable.lightrainshowers_day,
    "lightrainshowers_night" to R.drawable.lightrainshowers_night,
    "lightrainshowers_polartwilight" to R.drawable.lightrainshowers_polartwilight,
    "heavyrainshowers_day" to R.drawable.heavyrainshowers_day,
    "heavyrainshowers_night" to R.drawable.heavyrainshowers_night,
    "heavyrainshowers_polartwilight" to R.drawable.heavyrainshowers_polartwilight,
    "lightsleetshowers_day" to R.drawable.lightsleetshowers_day,
    "lightsleetshowers_night" to R.drawable.lightsleetshowers_night,
    "lightsleetshowers_polartwilight" to R.drawable.lightsleetshowers_polartwilight,
    "heavysleetshowers_day" to R.drawable.heavysleetshowers_day,
    "heavysleetshowers_night" to R.drawable.heavysleetshowers_night,
    "heavysleetshowers_polartwilight" to R.drawable.heavysleetshowers_polartwilight,
    "lightsnowshowers_day" to R.drawable.lightsnowshowers_day,
    "lightsnowshowers_night" to R.drawable.lightsnowshowers_night,
    "lightsnowshowers_polartwilight" to R.drawable.lightsnowshowers_polartwilight,
    "heavysnowshowers_day" to R.drawable.heavysnowshowers_day,
    "heavysnowshowers_night" to R.drawable.heavysnowshowers_night,
    "heavysnowshowers_polartwilight" to R.drawable.heavysnowshowers_polartwilight,
    "lightrain" to R.drawable.lightrain,
    "lightsleet" to R.drawable.lightsleet,
    "heavysleet" to R.drawable.heavysleet,
    "lightsnow" to R.drawable.lightsnow,
    "heavysnow" to R.drawable.heavysnow,
)

@Composable
fun WarningIcon(warningVector: ImageVector) {
    Image(
        imageVector = warningVector,
        contentDescription = "Warning Icon",
        modifier = Modifier
            .size(100.dp, 100.dp)
            .background(Color.Transparent),
        contentScale = ContentScale.Fit
    )
}

@SuppressLint("MissingPermission", "SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()

    val forecastState = homeViewModel.forecastState.collectAsState().value.forecastNextHour
    val beachLocation by homeViewModel.beachLocation.collectAsState()
    val alertState = homeViewModel.metAlertsState.collectAsState().value
    val beachInfo = homeViewModel.beachDetails.collectAsState().value

    var clicked by rememberSaveable { mutableStateOf(false) }
    val areActiveAlerts = rememberSaveable { mutableStateOf(false) }
    val warningVectorWhite = rememberWarning(false)
    val warningVectorRed = rememberWarning(true)
    val showNormalScreen: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val showNoAlertDisplay: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val showAlertDisplay: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val noLocation by homeViewModel.noLocation.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val localLoading: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
    val isConnectivityIssue = homeViewModel.isConnectivityIssue.collectAsState()

    val side1 = 450
    val side2 = 240

    val circleGradient = Brush.radialGradient(
        listOf(colorScheme.secondaryContainer, colorScheme.primary),
        center = Offset(side1 / 3.5f, side2 / 2.0f),
        radius = side1 / 1.54f,
        tileMode = TileMode.Clamp
    )
    val imageModifier = Modifier
        .size(140.dp)
        .clip(CircleShape)
        .border(
            BorderStroke(
                10.dp, colorScheme.primary
            ), CircleShape
        )
        .padding(10.dp)
        .background(
            circleGradient
        )
    LaunchedEffect(alertState.alerts) {
        areActiveAlerts.value = alertState.alerts.isNotEmpty()
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        if (isConnectivityIssue.value) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = "Kunne ikke laste informasjon.\nVennligst sjekk nettverkstilkoblingen din"
                )
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .background(colorScheme.primaryContainer)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(50.dp))
                Column(
                    modifier = Modifier.defaultMinSize(400.dp, 240.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier.size(400.dp, 200.dp)
                    ) {
                        Card(
                            elevation = CardDefaults.elevatedCardElevation(8.dp),
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(10.dp))
                                .align(Alignment.BottomCenter)
                                .padding(20.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(colorScheme.primary)
                                    .size(310.dp, 100.dp)
                            ) {
                                if (forecastState != null) {
                                    val tempText = "${forecastState.temp}°"
                                    val precipitationText = "${forecastState.precipitation} mm"
                                    Column(
                                        modifier = Modifier.size(100.dp, 100.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Text(
                                                text = "Oslo",
                                                fontSize = 15.sp,
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = 10.dp, vertical = 5.dp
                                                    )
                                                    .align(Alignment.TopStart),
                                                color = colorScheme.inverseOnSurface,
                                            )
                                            Text(
                                                text = tempText.dropLast(3) + "°C",
                                                fontSize = 34.sp,
                                                modifier = Modifier
                                                    .align(Alignment.BottomEnd)
                                                    .padding(8.dp),
                                                color = colorScheme.inverseOnSurface,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Column(
                                        modifier = Modifier.size(110.dp, 100.dp)
                                    ) {
                                        Spacer(
                                            modifier = Modifier.size(110.dp, 100.dp)
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.size(100.dp, 100.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Column(
                                            modifier = Modifier.size(100.dp, 30.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                if (precipitationText != "0.0 mm") {
                                                    Text(
                                                        text = precipitationText,
                                                        fontSize = 15.sp,
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = 10.dp, vertical = 5.dp
                                                            )
                                                            .align(Alignment.TopEnd),
                                                        color = colorScheme.inverseOnSurface,
                                                    )
                                                }
                                            }
                                        }
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .align(Alignment.CenterHorizontally)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(60.dp, 60.dp)
                                                        .align(Alignment.Center)
                                                ) {
                                                    Button(
                                                        onClick = {
                                                            clicked = !clicked
                                                        }, modifier = Modifier.padding(5.dp)
                                                    ) {}
                                                    if (areActiveAlerts.value) {
                                                        WarningIcon(warningVector = warningVectorRed)
                                                    } else {
                                                        WarningIcon(warningVector = warningVectorWhite)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box {
                                imageMap["clearsky_day"]?.let { painterResource(it) }?.let {
                                    Image(
                                        painter = it,
                                        modifier = imageModifier,
                                        alignment = Alignment.TopCenter,
                                        colorFilter = ColorFilter.tint(Color.White),
                                        contentDescription = "Laster værikon",
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                if (forecastState != null) {
                                    val imageName = forecastState.symbolCode
                                    val imageID = imageMap[imageName]
                                    if (imageID != null) {
                                        val image = painterResource(id = imageID)
                                        Image(
                                            painter = image,
                                            modifier = imageModifier,
                                            alignment = Alignment.TopCenter,
                                            contentDescription = "Værikon",
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                if (!areActiveAlerts.value && !clicked && !showNoAlertDisplay.value && !showAlertDisplay.value) {
                    showNormalScreen.value = true
                }
                if (!areActiveAlerts.value && clicked) {
                    showNoAlertDisplay.value = true
                }
                if (!areActiveAlerts.value && !clicked) {
                    showNoAlertDisplay.value = false
                }
                if (areActiveAlerts.value && !clicked) {
                    showAlertDisplay.value = true
                }
                if (areActiveAlerts.value && clicked) {
                    showAlertDisplay.value = false
                }
                Column {
                    if (showNormalScreen.value && !showNoAlertDisplay.value && !showAlertDisplay.value) {
                        NormalDisplay()
                    } else if (showAlertDisplay.value) {
                        AlertDisplay(alertState)
                    } else if (showNoAlertDisplay.value) {
                        NoAlertDisplay()
                        LaunchedEffect(Unit) {
                            delay(3000)
                            showNoAlertDisplay.value = false
                            showNormalScreen.value = !showNormalScreen.value
                            clicked = !clicked
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorScheme.background)
                    ) {
                        Column(
                            Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = if (noLocation) "Badesteder" else "Badesteder nær deg",
                                    modifier = Modifier
                                        .padding(top = 15.dp, bottom = 20.dp)
                                        .align(Alignment.Center),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                IconButton(
                                    onClick = {
                                        homeViewModel.refreshBeachLocations()
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .size(48.dp)
                                        .align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Refresh,
                                        contentDescription = "Oppdater",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (localLoading.value) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                                if (isLoading) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                } else {
                                    val state = rememberLazyListState()
                                    LazyRow(
                                        state = state,
                                        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = PaddingValues(6.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        items(beachLocation) { beach ->
                                            beach.second.let {
                                                localLoading.value = false
                                                BeachCard(
                                                    beach.first, it, navController, beachInfo
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun AlertDisplay(alertState: MetAlertsUIState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Box(
            modifier = Modifier
                .size(310.dp, 90.dp)
                .verticalNegativePadding(70.dp)
                .background(colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
                    items(alertState.alerts) { alert ->
                        MetAlertCard(weatherWarning = alert)
                    }
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun NoAlertDisplay() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.Bottom)
    ) {
        Box(
            modifier = Modifier
                .size(310.dp, 190.dp)
                .verticalNegativePadding(70.dp)
                .background(colorScheme.primary),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 110.dp, top = 10.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.Bottom)
            ) {
                Card(
                    modifier = Modifier
                        .width(290.dp)
                        .height(90.dp)
                        .padding(10.dp, 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ingen varsler",
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun NormalDisplay() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.Bottom)
    ) {
        Box(
            modifier = Modifier
                .size(310.dp, 190.dp)
                .verticalNegativePadding(50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp, top = 9.dp)
            ) {
                Text(
                    text = "Her har vi samlet Oslos beste badeperler for deg!",
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = colorScheme.primary,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 1.1.em, platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ), lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Proportional,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun MetAlertCard(weatherWarning: WeatherWarning) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(12.dp),
        modifier = Modifier
            .width(290.dp)
            .padding(10.dp, 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "FAREVARSEL",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 1.5.em, platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ), lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Top,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    ),
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
            val textArea = "Farevarsel for " + weatherWarning.area.lowercase() + ".\n"
            val textInstruction = "\n${weatherWarning.instruction}"
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = textArea + weatherWarning.description + textInstruction,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 1.2.em, platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ), lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.LastLineBottom
                            )
                        )
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
