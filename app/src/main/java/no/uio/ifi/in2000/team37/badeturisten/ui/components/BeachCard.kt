package no.uio.ifi.in2000.team37.badeturisten.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun beachCard(beach: Beach, navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer,
        ) {
        Card(
            onClick = { navController.navigate("beachProfile/${beach.name}")},
            modifier = Modifier
                .padding(100.dp)
                .fillMaxWidth()
                //.wrapContentWidth(Alignment.CenterHorizontally)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = Color.White)
                    .width(300.dp)
                    .height(80.dp)
            ) {
                Text(
                    text = beach.name,
                   textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                    )
                //knapp for favoritt, endre senere
                favouriteButton(beach)
                Text(
                    text = "Badetemperatur: ${beach.waterTemp}°C",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                /*Image(
                painter = rememberImagePainter(partyInfo.img),
                contentDescription = partyInfo.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

             */

            }
        }
    }
}
