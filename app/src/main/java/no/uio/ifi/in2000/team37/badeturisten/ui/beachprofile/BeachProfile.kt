package no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import no.uio.ifi.in2000.team37.badeturisten.R
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BottomBar
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.BeachViewModel

import com.airbnb.lottie.compose.LottieAnimation

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


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeachProfile(
    beachViewModel: BeachViewModel = viewModel(),
    navController: NavController,
    beachName: String?
) {
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
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            val imageUrl = beach.badevannsinfo?.bilde ?: "https://i.ibb.co/7KSxKnD/fis.webp"

                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Bilde fra Oslo Kommune",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .align(Alignment.Center)
                            )

                            LottieAnimation()

                            beach.beach?.let {
                                Text(
                                    text = it.name,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    style =
                                    TextStyle(color = Color.Black,
                                        drawStyle = Stroke(width = 15f
                                        )//join = StrokeJoin.Round)
                                    ),
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .basicMarquee()
                                        .padding(16.dp),
                                )
                                Text(
                                    text = it.name,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier
                                        .basicMarquee()
                                        .align(Alignment.TopCenter)
                                        .basicMarquee()
                                        .padding(16.dp),

                                style = TextStyle(color = Color.Black)
                                    //join = StrokeJoin.Round)
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
                            .fillMaxSize(),
                        border = BorderStroke(1.5.dp, Color.LightGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxSize()
                        ) {
                            Column {
                                if(beach.beach?.waterTemp!=null) {
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
                                }
                                if(beach.badevannsinfo?.kvalitetInfo != null){
                                    Row() {
                                        Text(
                                            text = "Vannkvalitet",
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier
                                                .padding(10.dp),

                                            )
                                        Spacer(modifier = Modifier.width(110.dp))

                                        beach.badevannsinfo?.kvalitetInfo?.let {
                                            Text(
                                                text = it,
                                                modifier = Modifier
                                                    .padding(10.dp),

                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                if (beach.badevannsinfo?.fasiliteterInfo != null) {
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
                                    }?: Text(text = "Ingen informasjon om fasiliteter for ${beach.badevannsinfo?.fasiliteterInfo}")
                                }
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }
            }
        }
    }