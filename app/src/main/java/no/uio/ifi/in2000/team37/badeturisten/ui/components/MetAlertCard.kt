package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.team37.badeturisten.data.MetAlerts.WeatherWarning

@Composable
fun MetAlertCard(weatherWarning: WeatherWarning): Boolean {
    if (weatherWarning.status == "Aktiv") {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
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
        return true
    }
    return false

}


