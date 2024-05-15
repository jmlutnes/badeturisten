package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachAndBeachInfo

@Composable
fun GradientUp(brush: Brush, waterTemp: Double?) {
    val modifier = if (waterTemp != null) {
        Modifier
            .fillMaxWidth()
            .height(150.dp)
    } else {
        Modifier
            .fillMaxWidth()
            .height(90.dp)
    }

    Canvas(modifier = modifier, onDraw = {
        drawRect(brush)
    })
}

@Composable
fun GradientDown(brush: Brush) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp), onDraw = {
        drawRect(brush)
    })
}

@Composable
fun BeachCard(
    beach: Beach,
    distance: Int,
    navController: NavController,
    beachInfoMap: Map<String, BeachAndBeachInfo?>,
) {
    val customBlue = Color(0xFF2E4064)
    val brushUp = Brush.verticalGradient(listOf(customBlue.copy(alpha = 0.9F), Color.Transparent))
    val brushDown = Brush.verticalGradient(listOf(Color.Transparent, customBlue.copy(alpha = 0.8F)))

    val beachInfo = beachInfoMap[beach.name]

    Card(
        onClick = { navController.navigate("beachProfile/${beach.name}") },
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .height(240.dp)
            .width(180.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
        ) {
            val km = distance / 1000.0
            Box(Modifier.fillMaxSize()) {
                val imageUrl = beachInfo?.info?.imageUrl
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
                Box(modifier = Modifier.align(Alignment.TopStart)) {
                    GradientUp(
                        brushUp, beach.waterTemp
                    )
                }
                if (distance > 1) {
                    Box(modifier = Modifier.align(Alignment.BottomEnd)) { GradientDown(brushDown) }
                }
                Column(
                    modifier = Modifier.align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = beach.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(
                            letterSpacing = 0.2.sp,
                            color = Color.White,
                        ),
                        modifier = Modifier.padding(
                                top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp
                            ), // Reduced bottom padding
                        textAlign = TextAlign.Center
                    )

                    val tempText =
                        if (beach.waterTemp != null) "${beach.waterTemp}°C i vannet" else ""

                    Text(
                        text = tempText,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            letterSpacing = 0.2.sp,
                            color = Color.White,
                        ),
                    )
                }

                if (distance > 1) {
                    Text(
                        text = "${String.format("%.1f", km)} km",
                        fontSize = 28.sp,
                        style = TextStyle(
                            letterSpacing = 0.2.sp,
                            color = Color.White,
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                    )
                }
            }
        }
    }
}
