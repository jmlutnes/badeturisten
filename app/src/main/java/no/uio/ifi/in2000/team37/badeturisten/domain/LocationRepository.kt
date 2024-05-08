package no.uio.ifi.in2000.team37.badeturisten.domain

import android.location.Location
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {
    val locationData: StateFlow<Location?>

    /**
     * Fetches last recorded location
     */
    fun fetchLastLocation()

    /**
     * Fetches current location
     */
    fun fetchCurrentLocation()
}
