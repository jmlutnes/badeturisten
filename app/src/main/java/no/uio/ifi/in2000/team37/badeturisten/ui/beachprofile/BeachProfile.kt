package no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import no.uio.ifi.in2000.team37.badeturisten.R
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Transportation(beach: BeachUIState) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Kollektivruter",
                modifier = Modifier
                    .padding(10.dp),
                fontWeight = FontWeight.SemiBold
            )
            Column(
                Modifier
                    .fillMaxSize()
            ) {
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
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Row(
                            Modifier
                                .fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "$transport ${it.linje}",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column(
                            Modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                Modifier
                                    .fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = it.navn,
                                    modifier = Modifier
                                        .basicMarquee()
                                        .align(Alignment.CenterVertically),
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Gradient() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
        val customBlue = Color(0xFF2E4064)
        val brushUp =
            Brush.verticalGradient(listOf(customBlue.copy(alpha = 0.9F), Color.Transparent))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            onDraw = {
                drawRect(brushUp)
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun BeachProfile(
    navController: NavController,
    beachName: String?,
) {
    val beachViewModel: BeachViewModel = hiltViewModel()
    val beach = beachViewModel.beachUIState.collectAsState().value
    val isLoading by beachViewModel.isLoading.collectAsState()
    // Collect favorite status updates
    val isFavorited by beachViewModel.isFavorited.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        // Upon initial composition, check and update the favorites
        beachViewModel.checkAndUpdateFavorites(beach)
    }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxHeight(),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                        ) {
                            Column(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()

                                ) {
                                    val imageUrl = beach.badevannsinfo?.imageUrl
                                        ?: "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp"

                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Bilde fra Oslo Kommune",
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(16.dp))
                                            .align(Alignment.Center)
                                    )
                                    Gradient()
                                    if (imageUrl == "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp") {
                                        LottieAnimation()
                                    }
                                    beach.beach?.let {
                                        Text(
                                            text = it.name,
                                            fontSize = 25.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                //.basicMarquee()
                                                .align(Alignment.TopCenter)
                                                .padding(16.dp),
                                            style = TextStyle(
                                                letterSpacing = 0.4.sp,
                                                color = Color.White,
                                            )
                                        )
                                    }
                                    IconButton(modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(17.dp),
                                        onClick = {
                                            beach.beach?.let {
                                                beachViewModel.updateFavourites(
                                                    it
                                                )
                                            }
                                        }) {
                                        val isFavorited =
                                        Icon(
                                            imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                                            contentDescription = "Heart",
                                            tint = if (isFavorited) Color.Red else Color.White,
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(
                            Modifier
                                .height(10.dp)
                        )
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            elevation = CardDefaults.elevatedCardElevation(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                Column {
                                    if (beach.beach?.waterTemp != null) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Badetemperatur",
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .padding(10.dp),
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Text(
                                                text = "${beach.beach.waterTemp}°C",
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .align(Alignment.CenterVertically)
                                            )
                                        }
                                    }
                                    if (beach.badevannsinfo?.waterQuality != null) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Vannkvalitet",
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .align(Alignment.CenterVertically)
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            beach.badevannsinfo.waterQuality.let {
                                                Text(
                                                    text = it,
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .align(Alignment.CenterVertically),
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        if (beach.badevannsinfo?.facilitiesInfo != null) {
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                                    .defaultMinSize(400.dp, 300.dp),
                                elevation = CardDefaults.elevatedCardElevation(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .fillMaxSize()
                                        .defaultMinSize(400.dp, 300.dp)
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "Fasiliteter",
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        beach.badevannsinfo.facilitiesInfo.let {
                                            Column(
                                                modifier = Modifier
                                                    .padding(4.dp)
                                            ) {
                                                Text(
                                                    text = it,
                                                    style = LocalTextStyle.current.merge(
                                                        TextStyle(
                                                            lineHeight = 2.0.em,
                                                            platformStyle = PlatformTextStyle(
                                                                includeFontPadding = false
                                                            ),
                                                            lineHeightStyle = LineHeightStyle(
                                                                alignment = LineHeightStyle.Alignment.Center,
                                                                trim = LineHeightStyle.Trim.None
                                                            )
                                                        )
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (beach.kollektivRute.isEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                        } else {
                            Transportation(beach)
                        }
                    }
                }
            }
        }
    }
}
        