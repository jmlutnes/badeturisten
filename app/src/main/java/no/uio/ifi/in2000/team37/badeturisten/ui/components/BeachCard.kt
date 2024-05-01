package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachInfoForHomescreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BeachCard(
    beach: Beach,
    avstand: Int,
    navController: NavController,
    beachinfo: BeachInfoForHomescreen?
) {
    Card(
        onClick = { navController.navigate("beachProfile/${beach.name}") },
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .height(240.dp)
            .width(180.dp),
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(Modifier.fillMaxSize()) {
                val imageUrl = beachinfo?.info?.imageUrl
                    ?: "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Bilde fra Oslo Kommune",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .align(Alignment.Center)
                )
                Text(
                    text = beach.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    style =
                    TextStyle(
                        color = Color.Black,
                        drawStyle = Stroke(
                            width = 15f
                        )
                    ),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        //.basicMarquee()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = beach.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        //.basicMarquee()
                        .align(Alignment.TopCenter)
                        .padding(16.dp),
                    style = TextStyle(color = Color.Black),
                    textAlign = TextAlign.Center
                )
                if(avstand>1) {
                    Text(
                        text = "${avstand} m",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        style =
                        TextStyle(
                            color = Color.Black,
                            drawStyle = Stroke(
                                width = 15f
                            )
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            //.basicMarquee()
                            .padding(16.dp),
                    )
                    Text(
                        text = "${avstand} m",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            //.basicMarquee()
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        style = TextStyle(color = Color.Black)
                    )
                }
                val tempText =
                    if (beach.waterTemp != null) "${beach.waterTemp}°C \ni vannet" else ""
                Text(
                    text = tempText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style =
                    TextStyle(
                        color = Color.Black,
                        drawStyle = Stroke(
                            width = 15f
                        )
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        //.basicMarquee()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = tempText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        //.basicMarquee()
                        .align(Alignment.Center)
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = Color.Black)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Badeinfoforbeachcard(
    beach: Beach,
    avstand: Int,
    navController: NavController,
    beachInfoMap: Map<String, BeachInfoForHomescreen?>
) {
    beachInfoMap[beach.name]?.let { badeinfo ->
        BeachCard(beach = beach, avstand = avstand, navController = navController, badeinfo)
    } ?: run {
        Card(
            onClick = { navController.navigate("beachProfile/${beach.name}") },
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .height(240.dp)
                .width(180.dp),
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(Modifier.fillMaxSize()) {
                    val imageUrl =
                        "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp"
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Bilde fra Oslo Kommune",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .align(Alignment.Center)
                    )
                    Text(
                        text = beach.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style =
                        TextStyle(
                            color = Color.Black,
                            drawStyle = Stroke(
                                width = 15f
                            )
                        ),
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            //.basicMarquee()
                            .padding(16.dp),
                    )
                    Text(
                        text = beach.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            //.basicMarquee()
                            .align(Alignment.TopCenter)
                            .padding(16.dp),
                        style = TextStyle(color = Color.Black)
                    )
                    if(avstand>1) {
                        Text(
                            text = "${avstand} m",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            style =
                            TextStyle(
                                color = Color.Black,
                                drawStyle = Stroke(
                                    width = 15f
                                )
                            ),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                //.basicMarquee()
                                .padding(16.dp),
                        )
                        Text(
                            text = "${avstand} m",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                //.basicMarquee()
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            style = TextStyle(color = Color.Black)
                        )
                    }
                    val tempText =
                        if (beach.waterTemp != null) "${beach.waterTemp}°C \ni vannet" else ""
                    Text(
                        text = tempText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style =
                        TextStyle(
                            color = Color.Black,
                            drawStyle = Stroke(
                                width = 15f
                            )
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                            //.basicMarquee()
                            .padding(16.dp),
                    )
                    Text(
                        text = tempText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            //.basicMarquee()
                            .align(Alignment.Center)
                            .padding(16.dp),
                        style = TextStyle(color = Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

