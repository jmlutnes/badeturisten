package no.uio.ifi.in2000.team37.badeturisten.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.components.beachCard
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.WaterTempViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), waterTempViewModel: WaterTempViewModel = viewModel(), navController : NavController) {
    val forecastState = homeViewModel.forecastState.collectAsState().value.forecastNextHour
    val waterTemperatureUIState = waterTempViewModel.waterTemperatureState.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
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
                .background(MaterialTheme.colorScheme.primaryContainer)) {

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
                            var tempText = ""
                            var precipitationText = ""
                            if (forecastState != null) {
                                tempText = "${forecastState.temp}°"
                                precipitationText = "${forecastState.precipitation} mm"
                            }
                            Text(text = tempText,
                                fontSize = 30.sp)
                            Text(text = precipitationText,
                                fontSize = 30.sp)
                        }
                    }

                    Spacer(Modifier.height(300.dp))

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

                        LazyColumn (Modifier
                            //.padding(innerPadding)
                            .background(MaterialTheme.colorScheme.primary)
                        ){
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

    // meny i top bar/venstre oevre hjoerne
    //luft temp
    //vear ikon
    //varsel knapp/melding
    //strand kort (near deg)


//luft temp
//navn
//bilde
//favoritt
// statistikk
// anmeldelser


