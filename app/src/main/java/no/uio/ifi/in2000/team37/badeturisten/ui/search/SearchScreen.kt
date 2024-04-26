package no.uio.ifi.in2000.team37.badeturisten.ui.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.components.Badeinfoforbeachcard

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
        Text(text, modifier = Modifier,
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
    navController: NavController
) {
    val searchViewModel: SearchViewModel = hiltViewModel()


    val searchResult by searchViewModel.sokResultater.collectAsState()
    val beachState = searchViewModel.beachState.collectAsState().value
    val beachinfo = searchViewModel.beachDetails.collectAsState().value

    val state = rememberLazyListState()
    var searchText by remember { mutableStateOf("") }

    Column {
        Column(modifier = Modifier
            .height(150.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Søk etter strender") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            val filtrerte = beachState.beaches.filter { strand ->
                strand.name.contains(searchText, ignoreCase = true)
            }

            LazyColumn {
                items(filtrerte) { strand ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("beachProfile/${strand.name}")
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
            LaunchedEffect(
                searchViewModel.badevakt.value,
                searchViewModel.barnevennlig.value,
                searchViewModel.grill.value,
                searchViewModel.kiosk.value,
                searchViewModel.tilpasning.value,
                searchViewModel.toalett.value,
                searchViewModel.badebrygge.value
            ) {
                searchViewModel.loadBeachesByFilter(
                    searchViewModel.badevakt.value,
                    searchViewModel.barnevennlig.value,
                    searchViewModel.grill.value,
                    searchViewModel.kiosk.value,
                    searchViewModel.tilpasning.value,
                    searchViewModel.toalett.value,
                    searchViewModel.badebrygge.value
                )
            }

            Column(
                Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
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
                            badevakt = searchViewModel.badevakt.value,
                            onBadevaktChange = {
                                searchViewModel.updateFilterState(
                                    "Badevakt",
                                    !searchViewModel.badevakt.value
                                )
                            },
                            barnevennlig = searchViewModel.barnevennlig.value,
                            onBarnevennligChange = {
                                searchViewModel.updateFilterState(
                                    "Barnevennlig",
                                    !searchViewModel.barnevennlig.value
                                )
                            },
                            grill = searchViewModel.grill.value,
                            onGrillChange = {
                                searchViewModel.updateFilterState(
                                    "Grill",
                                    !searchViewModel.grill.value
                                )
                            },
                            kiosk = searchViewModel.kiosk.value,
                            onKioskChange = {
                                searchViewModel.updateFilterState(
                                    "Kiosk",
                                    !searchViewModel.kiosk.value
                                )
                            },
                            tilpasning = searchViewModel.tilpasning.value,
                            onTilpasningChange = {
                                searchViewModel.updateFilterState(
                                    "Tilpasning",
                                    !searchViewModel.tilpasning.value
                                )
                            },
                            toalett = searchViewModel.toalett.value,
                            onToalettChange = {
                                searchViewModel.updateFilterState(
                                    "Toalett",
                                    !searchViewModel.toalett.value
                                )
                            },
                            badebrygge = searchViewModel.badebrygge.value,
                            onBadebryggeChange = {
                                searchViewModel.updateFilterState(
                                    "Badebrygge",
                                    !searchViewModel.badebrygge.value
                                )
                            }
                        )

                        LazyColumn(
                            state = state,
                            flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(searchResult.beachList) { beach ->
                                Badeinfoforbeachcard(beach, 0, navController, beachinfo)
                            }
                        }
                    }
                }
            }
        }
    }








