package no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import no.uio.ifi.in2000.team37.badeturisten.R
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BottomBar
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.BeachViewModel

import com.airbnb.lottie.compose.LottieAnimation
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.BeachUIState
import java.util.Locale

@Composable
fun LottieAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fisker))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}

@Composable
fun Kollektiv(beach: BeachUIState) {
//Kollektivruter
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .defaultMinSize(400.dp, 300.dp),
        border = BorderStroke(1.5.dp, Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .defaultMinSize(400.dp, 300.dp)
                .padding(10.dp),
            //contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = "Kollektivruter",
                    fontWeight = FontWeight.SemiBold
                )
                beach.kollektivRute.forEach() {
                    val transport = when (it.transportMode) {
                        "bus" -> "Buss"
                        "water" -> "Båt"
                        "rail" -> "Tog"
                        "tram" -> "Trikk"
                        "metro" -> "T-Bane"
                        "coach" -> "Buss"
                        else -> it.transportMode.replaceFirstChar { letter ->
                            if (letter.isLowerCase()) letter.titlecase(
                                Locale.getDefault()
                            ) else letter.toString()
                        }
                    }
                    Text(
                        text = "• ${transport} ${it.linje} \n${it.navn}",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}


    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BeachProfile(
        beachViewModel: BeachViewModel = viewModel(),
        navController: NavController,
        beachName: String?
    ) {
        val beach = beachViewModel.beachUIState.collectAsState().value

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
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxHeight(),
                        color = MaterialTheme.colorScheme.primaryContainer,
                    ) {
                        Column(
                            Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            beach.beach?.let {
                                Text(
                                    text = " ${it.name} ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        //.align(Alignment.TopCenter)
                                        .padding(top = 16.dp)
                                )
                            }
                            LottieAnimation()
                        }
                    }
                    Spacer(
                        Modifier
                            .height(15.dp)
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                    )

                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        border = BorderStroke(1.5.dp, Color.LightGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxSize()
                        ) {
                            Column {
                                Row() {
                                    Text(
                                        text = "Badetemperatur",
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .align(Alignment.Top)
                                            .padding(10.dp),
                                    )
                                    Spacer(modifier = Modifier.width(100.dp))
                                    beach.beach?.waterTemp?.let {
                                        Text(
                                            text = "${it}°C",
                                            modifier = Modifier
                                                .padding(10.dp),

                                            )
                                    } ?: Text(
                                        text = "Ingen informasjon.",
                                        modifier = Modifier.padding(top = 16.dp)
                                    )
                                }
                                Row() {
                                    Text(
                                        text = "Vannkvalitet",
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .padding(10.dp),

                                        )
                                    Spacer(modifier = Modifier.width(100.dp))
                                    beach.badevannsinfo?.kvalitetInfo?.let {
                                        Text(
                                            text = it,
                                            modifier = Modifier
                                                .padding(10.dp),

                                            )
                                    } ?: Text(
                                        text = "Ingen informasjon.",
                                        modifier = Modifier
                                            .padding(10.dp),

                                        )
                                }

                            }

                        }

                    }

                    Spacer(
                        Modifier
                            .height(15.dp)
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                    )

                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .defaultMinSize(400.dp, 300.dp),
                        border = BorderStroke(1.5.dp, Color.LightGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxSize()
                                .defaultMinSize(400.dp, 300.dp)
                                .padding(10.dp),
                            //contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Text(
                                    text = "Fasiliteter",
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                beach.badevannsinfo?.fasiliteterInfo?.let {
                                    Text(text = it)
                                    //Text("stjerner her?")
                                }
                                    ?: Text(text = "Ingen informasjon.")
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        )
                    }

                    if (beach.kollektivRute.isEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                    } else {
                        Kollektiv(beach)
                    }
                }
            }
        }
    }

