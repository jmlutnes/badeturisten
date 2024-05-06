package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning

@Composable
fun MetAlertCard(weatherWarning: WeatherWarning) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(12.dp),
            modifier = Modifier
                .width(290.dp)
                .padding(10.dp, 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
                                    alignment = LineHeightStyle.Alignment.Top,
                                    trim = LineHeightStyle.Trim.None
                                )
                            )
                        ),
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )
                }
                val tekstArea = "Farevarsel for " + weatherWarning.area.lowercase() + ".\n"
                val tekstInstruks = "\n${weatherWarning.instruction}"
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = tekstArea + weatherWarning.description + tekstInstruks,
                        fontSize = 12.sp,
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
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }


