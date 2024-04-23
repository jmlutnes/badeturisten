package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.animation.expandVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MetAlertCard(weatherWarning: WeatherWarning): Boolean {
    //if (weatherWarning.status == "Aktiv") {
    Card(
        modifier = Modifier
            .size(150.dp, 190.dp)
            .padding(10.dp, 10.dp)
        ,

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(
                text = "FAREVARSEL",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                    lineHeight = 1.5.em,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                )
            )
                    ,
                    color = Color.Red
            )
            //Text(text = "Område: ${weatherWarning.area}",
            //   fontSize = 13.sp)
            Text(text = "${weatherWarning.description}",
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeight = 1.2.em,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.LastLineBottom
                        )
                    )
                )
            )

           // Text(text = "Event:  ${weatherWarning.event}",
             //   fontSize = 13.sp)
            //Text(text = "Alvorlighetsgrad: ${weatherWarning.severity}", fontSize = 13.sp)
            //Text(text = "Instruksjon: ${weatherWarning.instruction}", fontSize = 13.sp)
            //Text("Status: " + weatherWarning.status)
            //Text("Web: " + weatherWarning.web)
        }
    //}

    }
        return true
    //return false

}



