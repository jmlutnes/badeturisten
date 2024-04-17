package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning

@Composable
fun MetAlertCard(weatherWarning: WeatherWarning): Boolean {
    if (weatherWarning.status == "Aktiv") {
        Card(
            modifier = Modifier
                .size(310.dp, 100.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Text(
                    text = "FAREVARSEL",
                    fontWeight = FontWeight.Bold
                )
                Text("Område: " + weatherWarning.area)
                Text("Beskrivelse: " + weatherWarning.description)
                Text("Event: " + weatherWarning.event)
                Text("Alvorlighetsgrad: " + weatherWarning.severity)
                Text("Instruksjon: " + weatherWarning.instruction)
                //Text("Status: " + weatherWarning.status)
                //Text("Web: " + weatherWarning.web)
            }
        }
        return true
    }
    return false

}


