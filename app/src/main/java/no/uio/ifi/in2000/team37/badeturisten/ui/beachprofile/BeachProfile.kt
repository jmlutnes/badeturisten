package no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BottomBar
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.BeachViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeachProfile(beachViewModel: BeachViewModel = viewModel(), navController: NavController, beachName: String?) {
    val beach = beachViewModel.beachUIState.collectAsState().value
    //val badeinfo = beachViewModel.UiKommune.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Badested") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Column {
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) {
                                beach.beach?.let { Text(text = "${it.name}",
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(top = 16.dp)) }
                            }
                        }
                        Spacer(
                            Modifier
                                .height(15.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer))

                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .fillMaxWidth()
                                    .height(300.dp)
                            ) {
                                Column {
                                    Row() {
                                        Text(text = "Badetemperatur",
                                            modifier = Modifier
                                                .align(Alignment.Top)
                                                .padding(top = 16.dp))
                                        Spacer(modifier = Modifier.width(120.dp))
                                        Text(text = "${beach.beach?.waterTemp}°C",
                                            modifier = Modifier.padding(top = 16.dp))
                                    }
                                    Row() {
                                        Text(text = "Vannkvalitet",
                                            modifier = Modifier
                                                .align(Alignment.Top)
                                                .padding(top = 16.dp))
                                        Spacer(modifier = Modifier.width(100.dp))
                                        Text(text = "${beach.badevannsinfo?.kvalitetInfo}\n", modifier = Modifier
                                            .padding(top = 16.dp))

                                    }

                                }


                            }
                        }

                        Spacer(
                            Modifier
                                .height(15.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer))

                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .fillMaxWidth()
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    Text(text = "\n${beach.badevannsinfo?.title}")
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("stjerner her?")
                                }
                            }
                        }

                        Spacer(
                            Modifier
                                .height(500.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer))

                    }
                }
            }
        }
    }
}

