package no.uio.ifi.in2000.team37.badeturisten.ui.home


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.R
import no.uio.ifi.in2000.team37.badeturisten.ui.components.MetAlertCard
import no.uio.ifi.in2000.team37.badeturisten.ui.components.beachCard
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.window.DialogProperties
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.SwipeableState
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlinx.coroutines.launch

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

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalFoundationApi::class, ExperimentalWearMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val forecastState = homeViewModel.forecastState.collectAsState().value.forecastNextHour
    var beachList = homeViewModel.beachList
    if (beachList.isEmpty()) {
        homeViewModel.reloadBeaches()
        beachList = homeViewModel.beachList
    }
    val alertState = homeViewModel.metAlertsState.collectAsState().value

    var clicked by remember { mutableStateOf(false) }

    val imageModifier = Modifier
        .size(100.dp)
        .clip(CircleShape)
        .border(
            BorderStroke(
                10.dp,
                MaterialTheme.colorScheme.background
            ),
            CircleShape
        )
        .padding(5.dp)
        .background(Color.White)

    Scaffold( )
    { padding ->
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    Spacer(Modifier.height(50.dp)) // 300
                    Column(
                        modifier = Modifier,
                        //.fillMaxSize()
                        //.padding(padding),
                        //verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (forecastState != null) {
                            val imageName = forecastState.symbolCode
                            val imageID = imageMap[imageName]
                            if (imageID != null) {
                                val image = painterResource(id = imageID)
                                Image(
                                    painter = image,
                                    modifier = imageModifier,
                                    alignment = Alignment.Center,
                                    contentDescription = "Værikon",
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            var tempText = ""
                            var precipitationText = ""
                            if (forecastState != null) {
                                tempText = "${forecastState.temp}°"
                                precipitationText = "${forecastState.precipitation} mm"
                            }
                            Text(
                                text = tempText,
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.Start)
                                    .padding(10.dp)
                            )
                            Button(
                                onClick = {
                                    if (!clicked) {
                                        clicked = true
                                    } else {
                                        clicked = false
                                    }
                                },
                                modifier = Modifier
                                    //.weight(1f)
                                    //.wrapContentWidth(Alignment.End)
                                    .padding(10.dp),
                                //alignment = Alignment.Center,


                            ) {
                                Text(
                                    text = "Farevarsel",
                                )
                            }
                            Text(
                                text = precipitationText,
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.Start)
                                    .padding(10.dp)
                            )
                        }

                        Column(
                            Modifier
                                .background(Color.Transparent)
                        ) {


                            if (!clicked) {
                                Card {
                                    Text(
                                        text = "Hyggelig melding. Sola skinner og alle er glade tralalalala",
                                        modifier = Modifier
                                            .padding(20.dp)
                                    )

                                }
                            } else {
                                var aktiveVarsler: Boolean = false
                                LazyColumn {
                                    items(alertState.alerts) { weatherWarning ->
                                        if (MetAlertCard(weatherWarning = weatherWarning)) {
                                            aktiveVarsler = true
                                        }
                                    }
                                }
                                if (!aktiveVarsler) {
                                    Card(
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Ingen farevarsler",
                                            modifier = Modifier
                                                .padding(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(70.dp)) // 300
                    Box {
                        Modifier
                            //.padding(innerPadding)
                            .background(MaterialTheme.colorScheme.primary)
                        Column(
                            Modifier
                            //.padding(innerPadding)
                        ) {
                            Text(
                                text = "Badesteder",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            var pullUp by remember { mutableStateOf(false) }
                            val swipeableState = rememberSwipeableState(0)
                            var maxHeight by remember { mutableStateOf(0f) }
                            val state = rememberLazyListState()
                            val coroutineScope = rememberCoroutineScope()
                            LazyColumn(
                                modifier = Modifier
                                    .swipeable(
                                        state = swipeableState,
                                        anchors = mapOf(0f to 0, (maxHeight * 0.5f) to 1),
                                        orientation = Orientation.Vertical
                                    )
                                    .onGloballyPositioned { coordinates ->
                                        maxHeight = coordinates.size.height.toFloat()
                                        if (pullUp) {
                                            coroutineScope.launch {
                                                swipeableState.animateTo(
                                                    targetValue = (maxHeight * 0.5f).toInt()
                                                    //animationSpec = spring(),
                                                )
                                            }
                                        } else {
                                            coroutineScope.launch{
                                                swipeableState.animateTo(
                                                    targetValue = 0
                                                    //animationSpec = spring(),
                                                )
                                            }
                                        }
                                    },
                                    //.padding(innerPadding)
                                    //.background(Color.LightGray)
                                state = state,
                                flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
                            ) {
                                items(beachList) { beach ->
                                    beachCard(beach = beach, navController = navController)
                                }
                            }
                        }
                    }
                }
            }
    }
}