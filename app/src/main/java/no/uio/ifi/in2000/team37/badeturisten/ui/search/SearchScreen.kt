package no.uio.ifi.in2000.team37.badeturisten.ui.search

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.delay
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BeachCard

@Composable
fun CustomToggleButton(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = { onCheckedChange() }, colors = ButtonDefaults.buttonColors(
            if (checked) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = Color.White
        ), modifier = modifier
    ) {
        Text(
            text, modifier = Modifier, style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 10.sp,
                color = if (checked) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
            )
        )
    }
}

@Composable
fun FilterButtons(
    lifeguardOn: Boolean, onLifeguardChange: () -> Unit,
    childFriendlyOn: Boolean, onChildFriendlyChange: () -> Unit,
    grillOn: Boolean, onGrillChange: () -> Unit,
    kioskOn: Boolean, onKioskChange: () -> Unit,
    accessibleOn: Boolean, onAccessibleChange: () -> Unit,
    toiletsOn: Boolean, onToiletsChange: () -> Unit,
    divingTowerOn: Boolean, onDivingTowerChange: () -> Unit,
) {
    Box {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            CustomToggleButton(
                checked = lifeguardOn, onCheckedChange = onLifeguardChange, text = "Badevakt"
            )
            Spacer(Modifier.width(8.dp))

            CustomToggleButton(
                checked = childFriendlyOn,
                onCheckedChange = onChildFriendlyChange,
                text = "Barnevennlig"
            )
            Spacer(Modifier.width(8.dp))

            CustomToggleButton(
                checked = grillOn, onCheckedChange = onGrillChange, text = "Grill"
            )
            Spacer(Modifier.width(8.dp))

            CustomToggleButton(
                checked = kioskOn, onCheckedChange = onKioskChange, text = "Kiosk"
            )
            Spacer(Modifier.width(8.dp))

            CustomToggleButton(
                checked = accessibleOn,
                onCheckedChange = onAccessibleChange,
                text = "Tilpasset bevegelseshemmede"
            )
            Spacer(Modifier.width(8.dp))

            CustomToggleButton(
                checked = toiletsOn, onCheckedChange = onToiletsChange, text = "Toalett"
            )
            Spacer(Modifier.width(8.dp))

            CustomToggleButton(
                checked = divingTowerOn, onCheckedChange = onDivingTowerChange, text = "Badebrygge"
            )
        }
    }
}

@Composable
fun SearchScreen(
    navController: NavController,
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchResult by searchViewModel.searchResults.collectAsState()
    val beachState = searchViewModel.beachState.collectAsState().value
    val beachInfo = searchViewModel.beachDetails.collectAsState().value
    val state = rememberLazyGridState()
    var searchText by rememberSaveable { mutableStateOf("") }
    val isLoading by searchViewModel.isLoading.collectAsState()
    val localLoading: MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
    var noResultsMessage by remember { mutableStateOf("") }
    val lifeGuard = searchViewModel.lifeGuard.value
    val childFriendly = searchViewModel.childFriendly.value
    val grill = searchViewModel.grill.value
    val kiosk = searchViewModel.kiosk.value
    val accessible = searchViewModel.accessible.value
    val toilets = searchViewModel.toilets.value
    val divingTower = searchViewModel.divingTower.value
    val isConnectivityIssue = searchViewModel.isConnectivityIssue.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        if (isConnectivityIssue.value) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = "Kunne ikke laste informasjon.\nVennligst sjekk nettverkstilkoblingen din"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LaunchedEffect(
                lifeGuard, childFriendly, grill, kiosk, accessible, toilets, divingTower
            ) {
                searchViewModel.loadIntersectedBeaches(
                    lifeGuard, childFriendly, grill, kiosk, accessible, toilets, divingTower
                )
            }
            Column(
                Modifier.background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 60.dp),
                    ) {
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = "Søk etter badesteder",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            label = { Text("Søk") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                disabledContainerColor = MaterialTheme.colorScheme.background
                            )
                        )

                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FilterButtons(lifeguardOn = lifeGuard, onLifeguardChange = {
                            searchViewModel.updateFilterState(
                                "Badevakt", !lifeGuard
                            )
                        }, childFriendlyOn = childFriendly, onChildFriendlyChange = {
                            searchViewModel.updateFilterState(
                                "Barnevennlig", !childFriendly
                            )
                        }, grillOn = grill, onGrillChange = {
                            searchViewModel.updateFilterState(
                                "Grill", !grill
                            )
                        }, kioskOn = kiosk, onKioskChange = {
                            searchViewModel.updateFilterState(
                                "Kiosk", !kiosk
                            )
                        }, accessibleOn = accessible, onAccessibleChange = {
                            searchViewModel.updateFilterState(
                                "Tilpasning", !accessible
                            )
                        }, toiletsOn = toilets, onToiletsChange = {
                            searchViewModel.updateFilterState(
                                "Toalett", !toilets
                            )
                        }, divingTowerOn = divingTower, onDivingTowerChange = {
                            searchViewModel.updateFilterState(
                                "Badebrygge", !divingTower
                            )
                        })
                    }
                }
                val filtered = beachState.beaches.filter { beach ->
                    beach.name.contains(searchText, ignoreCase = true)
                }
                val anyActiveFiltering =
                    (lifeGuard || childFriendly || grill || kiosk || accessible || toilets || divingTower)
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
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    LaunchedEffect(
                        Unit,
                        filtered,
                        isLoading,
                        lifeGuard,
                        childFriendly,
                        grill,
                        kiosk,
                        accessible,
                        toilets,
                        divingTower
                    ) {
                        noResultsMessage = ""
                        delay(500)
                        noResultsMessage = "Ingen resultater"
                    }
                    if (localLoading.value) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(180.dp),
                            state = state,
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                            verticalArrangement = Arrangement.Top,
                            userScrollEnabled = !(localLoading.value)
                        ) {
                            val currentList = if (searchText == "" && !anyActiveFiltering) {
                                beachState.beaches.sortedBy { it.name }
                            } else if (!anyActiveFiltering) {
                                filtered
                            } else {
                                searchResult.beachList.intersect(filtered.toSet()).toList()
                            }
                            if (currentList.isEmpty() && !(localLoading.value)) {
                                item {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        Text(
                                            text = noResultsMessage,
                                            modifier = Modifier
                                                .align(Alignment.CenterStart)
                                                .padding(vertical = 30.dp, horizontal = 30.dp),
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            } else {
                                items(currentList) { beach ->
                                    localLoading.value = false
                                    BeachCard(beach, -1, navController, beachInfo)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}