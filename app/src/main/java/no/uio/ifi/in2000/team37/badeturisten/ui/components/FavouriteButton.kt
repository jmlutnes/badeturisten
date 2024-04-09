package no.uio.ifi.in2000.team37.badeturisten.ui.components

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

@Composable
fun favouriteButton(beach: Beach?) {
    Button(onClick = {
        if (beach != null) {
            beach.favorite = !beach.favorite
        }
        /*beach.copy(
            name = beach.name,
            pos = beach.pos,
            waterTemp = beach.waterTemp,
            favorite = !beach.favorite
        )*/
        if (beach != null) {
            Log.d("BeCa", "${beach.name}.favorite = ${beach.favorite}")
        }
    }) {
        Text(text = "hjerte")
    }
}