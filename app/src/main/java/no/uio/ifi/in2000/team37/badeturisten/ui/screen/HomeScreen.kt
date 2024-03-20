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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.semantics.Role.Companion.Button
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

@Composable
fun FareVarselCard() {
    Card{
        Text("Ingen farevarsler.")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), waterTempViewModel: WaterTempViewModel = viewModel(),
               metAlertsViewModel: MetAlertsViewModel = viewModel(), navController : NavController) {
    val temp = homeViewModel._locationTemperature.collectAsState().value.temp
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
        // Innhold her
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
                        Row {
                            Text(
                                text = "$temp °C",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.Start)
                                    .padding(16.dp)
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
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.End)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "Farevarsel"
                                ) // midlertidig --> må hente ikon

                            }
                        }

                        if (!clicked) {
                            Card {// liten melding basert på været

                                // hvis det er fint vær for bading
                                /*
                                Text("Solen skinner, og det er ikke så kaldt! " +
                                        "Det er en perfekt dag å ta med seg venner på bading!",
                                    modifier = Modifier
                                    .padding(20.dp))
                                    
                                 */

                                // hvis det er dårlig vær for bading
                                Text(
                                    "Det er farlig å være ute i dag. Ha på brodder." +
                                            "Ikke bad alene da havet kan suge deg opp.",
                                    modifier = Modifier
                                        .padding(20.dp)
                                )

                            }
                        }
                        var antIkkeAktiv = 0

                        if (clicked) {
                            LazyColumn {
                                var antIkkeAktiv = 0
                                //metAlertsViewModel.metAlertsUiState.alerts.size

                                items(metAlertsViewModel.metAlertsUiState.alerts) { weatherWarning ->
                                    if (weatherWarning.status == "Aktiv") {
                                        MetAlertCard(weatherWarning = weatherWarning)
                                    } else {
                                        antIkkeAktiv++;
                                    }
                                }


                            }
                            if (antIkkeAktiv == metAlertsViewModel.metAlertsUiState.alerts.size) {
                                Card {
                                    Text("Ingen farevarsler.")
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
    }
}




