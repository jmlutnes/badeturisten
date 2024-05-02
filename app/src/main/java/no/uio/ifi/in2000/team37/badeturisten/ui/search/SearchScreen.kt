package no.uio.ifi.in2000.team37.badeturisten.ui.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.components.Badeinfoforbeachcard

@Composable
fun CustomToggleButton(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = { onCheckedChange() },
        colors = ButtonDefaults.buttonColors(
            if (checked) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text(
            text,
            modifier = Modifier,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 10.sp,
                color =
                if (checked) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,


                )
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
    badebrygge: Boolean, onBadebryggeChange: () -> Unit,
) {
    Box {
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
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    navController: NavController,
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchResult by searchViewModel.sokResultater.collectAsState()
    val beachState = searchViewModel.beachState.collectAsState().value
    val beachinfo = searchViewModel.beachDetails.collectAsState().value
    val state = rememberLazyGridState()
    var searchText by remember { mutableStateOf("") }
    val isLoading by searchViewModel.isLoading.collectAsState()
    val localLoading: MutableState<Boolean> = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    )
    {
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
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 60.dp),
                    ) {
                        Spacer(Modifier.height(30.dp))
                        Text(
                            text = "Søk etter badesteder",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            label = { Text("Søk etter badesteder") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            )
                        )
                    }
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 10.dp)
                    ) {
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
                    }
                }
            }
            val filtrerte = beachState.beaches.filter { strand ->
                strand.name.contains(searchText, ignoreCase = true)
            }
            Text(
                text = "Søkeresultater",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                //.padding(horizontal = 30.dp, vertical = 15.dp),
                ,
                contentAlignment = Alignment.Center
            ) {
                if (localLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(180.dp),
                        state = state,
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                        verticalArrangement = Arrangement.Top,
                        userScrollEnabled = !(localLoading.value || isLoading)
                    ) {
                        val currentList = if (filtrerte.equals("")) {
                            searchResult.beachList
                        } else {
                            searchResult.beachList.intersect(filtrerte.toSet()).toList()
                        }
                        if (currentList.isEmpty() && !(isLoading || localLoading.value)) {
                            item {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        "Ingen resultater", modifier = Modifier
                                            .padding(16.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        } else {
                            items(currentList) { beach ->
                                localLoading.value = false
                                Badeinfoforbeachcard(beach, -1, navController, beachinfo)
                            }
                        }
                    }
                }
            }
        }
    }
}