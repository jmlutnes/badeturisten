package no.uio.ifi.in2000.team37.badeturisten.ui.search

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.network.NetworkUtils

import no.uio.ifi.in2000.team37.badeturisten.ui.components.beachCard
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeViewModel

@Composable
fun CustomToggleButton(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = { onCheckedChange() },
        colors = ButtonDefaults.buttonColors(
            if (checked) Color.Gray else Color.LightGray,
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text(
            text, modifier = Modifier,
            fontSize = 10.sp
        )
    }
}

@Composable
fun FilterButtons(
    badevakt: Boolean, onBadevaktChange: () -> Unit,
    barnevennlig: Boolean, onBarnevennligChange: () -> Unit,
    grill: Boolean, onGrillChange: () -> Unit,
    kiosk: Boolean, onKioskChange: () -> Unit,
    tilpasning: Boolean, onTilpasningChange: () -> Unit,
    toalett: Boolean, onToalettChange: () -> Unit,
    badebrygge: Boolean, onBadebryggeChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        CustomToggleButton(
            checked = badevakt,
            onCheckedChange = onBadevaktChange,
            text = "Badevakt"
        )
        Spacer(Modifier.width(8.dp))

        CustomToggleButton(
            checked = barnevennlig,
            onCheckedChange = onBarnevennligChange,
            text = "Barnevennlig"
        )
        Spacer(Modifier.width(8.dp))

        CustomToggleButton(
            checked = grill,
            onCheckedChange = onGrillChange,
            text = "Grill"
        )
        Spacer(Modifier.width(8.dp))

        CustomToggleButton(
            checked = kiosk,
            onCheckedChange = onKioskChange,
            text = "Kiosk"
        )
        Spacer(Modifier.width(8.dp))

        CustomToggleButton(
            checked = tilpasning,
            onCheckedChange = onTilpasningChange,
            text = "Tilpasset bevegelseshemmede"
        )
        Spacer(Modifier.width(8.dp))

        CustomToggleButton(
            checked = toalett,
            onCheckedChange = onToalettChange,
            text = "Toalett"
        )
        Spacer(Modifier.width(8.dp))

        CustomToggleButton(
            checked = badebrygge,
            onCheckedChange = onBadebryggeChange,
            text = "Badebrygge"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    sokViewModel: SokViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val sokResultater by sokViewModel.sokResultater.collectAsState()
    val state = rememberLazyListState()
    val beachList = homeViewModel.beachList
    var sokeTekst by remember { mutableStateOf("") }

    val context = LocalContext.current
    val isNetworkAvail = { NetworkUtils.isNetworkAvail(context) }

    Column {
        if (isNetworkAvail()) {
            Column(
                modifier = Modifier
                    .height(150.dp)
            ) {
                TextField(
                    value = sokeTekst,
                    onValueChange = { sokeTekst = it },
                    label = { Text("Søk etter strender") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                val filtrerte = beachList.filter { strand ->
                    strand.name.contains(sokeTekst, ignoreCase = true)
                }

                LazyColumn {
                    items(filtrerte) { strand ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isNetworkAvail()) {
                                        navController.navigate("beachProfile/${strand.name}")
                                    } else {
                                        Toast
                                            .makeText(
                                                context,
                                                "Ingen nettverkstilgjengelighet. Kan ikke vise detaljer for ${strand.name}.",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    }
                                }
                                .padding(16.dp)
                        ) {
                            Text(text = strand.name, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.weight(1f))
                            strand.waterTemp?.let {
                                Text(
                                    text = "Vann temp: $it°C",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            Column {
                if (isNetworkAvail()) {
                    LaunchedEffect(
                        sokViewModel.badevakt.value,
                        sokViewModel.barnevennlig.value,
                        sokViewModel.grill.value,
                        sokViewModel.kiosk.value,
                        sokViewModel.tilpasning.value,
                        sokViewModel.toalett.value,
                        sokViewModel.badebrygge.value
                    ) {
                        sokViewModel.loadBeachesByFilter(
                            sokViewModel.badevakt.value,
                            sokViewModel.barnevennlig.value,
                            sokViewModel.grill.value,
                            sokViewModel.kiosk.value,
                            sokViewModel.tilpasning.value,
                            sokViewModel.toalett.value,
                            sokViewModel.badebrygge.value
                        )
                    }
                } else {
                    Toast
                        .makeText(
                            context,
                            "Ingen nettverkstilgjengelighet. Kan ikke vise detaljer.",
                            Toast.LENGTH_LONG
                        )
                        .show()
                }
                Column(
                    Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    if (isNetworkAvail()) {
                        Column(modifier = Modifier.fillMaxHeight()) {
                            Text(
                                text = "Filtrert søk",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            FilterButtons(
                                badevakt = sokViewModel.badevakt.value,
                                onBadevaktChange = {
                                    sokViewModel.updateFilterState(
                                        "Badevakt",
                                        !sokViewModel.badevakt.value
                                    )
                                },
                                barnevennlig = sokViewModel.barnevennlig.value,
                                onBarnevennligChange = {
                                    sokViewModel.updateFilterState(
                                        "Barnevennlig",
                                        !sokViewModel.barnevennlig.value
                                    )
                                },
                                grill = sokViewModel.grill.value,
                                onGrillChange = {
                                    sokViewModel.updateFilterState(
                                        "Grill",
                                        !sokViewModel.grill.value
                                    )
                                },
                                kiosk = sokViewModel.kiosk.value,
                                onKioskChange = {
                                    sokViewModel.updateFilterState(
                                        "Kiosk",
                                        !sokViewModel.kiosk.value
                                    )
                                },
                                tilpasning = sokViewModel.tilpasning.value,
                                onTilpasningChange = {
                                    sokViewModel.updateFilterState(
                                        "Tilpasning",
                                        !sokViewModel.tilpasning.value
                                    )
                                },
                                toalett = sokViewModel.toalett.value,
                                onToalettChange = {
                                    sokViewModel.updateFilterState(
                                        "Toalett",
                                        !sokViewModel.toalett.value
                                    )
                                },
                                badebrygge = sokViewModel.badebrygge.value,
                                onBadebryggeChange = {
                                    sokViewModel.updateFilterState(
                                        "Badebrygge",
                                        !sokViewModel.badebrygge.value
                                    )
                                }
                            )

                            LazyColumn(
                                state = state,
                                flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                items(sokResultater.beachList) { beach ->
                                    beachCard(
                                        beach = beach,
                                        navController = navController,
                                        isNetworkAvail = isNetworkAvail,
                                        context = context
                                    )
                                }
                            }
                        }
                    } else {
                        Toast
                            .makeText(
                                context,
                                "Ingen nettverkstilgjengelighet. Kan ikke vise detaljer.",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                }
            }
        } else {
            Toast
                .makeText(
                    context,
                    "Ingen nettverkstilgjengelighet. Kan ikke vise detaljer.",
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }
}







