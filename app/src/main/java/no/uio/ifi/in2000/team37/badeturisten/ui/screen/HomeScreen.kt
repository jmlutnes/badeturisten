package no.uio.ifi.in2000.team37.badeturisten.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.R
import no.uio.ifi.in2000.team37.badeturisten.ui.components.MetAlertCard
import no.uio.ifi.in2000.team37.badeturisten.ui.components.beachCard
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.MetAlertsViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.WaterTempViewModel

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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    waterTempViewModel: WaterTempViewModel = viewModel(),
    metAlertsViewModel: MetAlertsViewModel = viewModel(),
    navController: NavController
) {
    val forecastState = homeViewModel.forecastState.collectAsState().value.forecastNextHour
    val waterTemperatureUIState = waterTempViewModel.waterTemperatureState.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    var clicked by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    /* Image( Hvis vi vil ha logo eller bilde senere
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(200.dp, 65.dp)
                    )
                    */
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Innholdsmeny"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },

        ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {

            BoxWithConstraints {
                Column(modifier = Modifier.fillMaxHeight()) {
                    Column {
                        Text(
                            text = "Været nå",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Default
                        )
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
                                    .padding(16.dp)
                            )
                            if (forecastState != null) {
                                val imageName = forecastState.symbolCode
                                val imageID = imageMap[imageName]
                                if (imageID != null) {
                                    val image = painterResource(id = imageID)
                                    Image(
                                        painter = image,
                                        contentDescription = null
                                    )
                                }
                            }
                            Text(
                                text = precipitationText,
                                fontSize = 30.sp
                            )
                        }
                    }
                    Column {
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
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Farevarsel"
                            )
                        }
                        if (!clicked) {
                            Card {
                                Text(
                                    "Det er farlig å være ute i dag. Ha på brodder." +
                                            "Ikke bad alene da havet kan suge deg opp.",
                                    modifier = Modifier
                                        .padding(20.dp)
                                )

                            }
                        }

                        if (clicked) {
                            var aktiveVarsler: Boolean = false
                            LazyColumn {
                                items(metAlertsViewModel.metAlertsUiState.alerts) { weatherWarning ->
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

                    Spacer(Modifier.height(200.dp)) // 300

                    Column {
                        Text(
                            text = "Badesteder",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        LazyColumn(
                            Modifier
                                //.padding(innerPadding)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            items(waterTemperatureUIState.beaches) { beach ->
                                beachCard(beach = beach, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}






