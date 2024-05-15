package no.uio.ifi.in2000.team37.badeturisten.data.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationRepository
import javax.inject.Inject

@Suppress("DEPRECATION")
class LocationRepositoryImp @Inject constructor(
    private val context: Context,
) : LocationRepository {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val _locationData = MutableStateFlow<Location?>(null)
    override val locationData: StateFlow<Location?> get() = _locationData.asStateFlow()

    override fun fetchLastLocation() {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            _locationData.value = null
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            _locationData.value = location
        }.addOnFailureListener {
            _locationData.value = null
        }
    }

    override fun fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            _locationData.value = null
            return
        }
        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token
        ).addOnSuccessListener { location: Location? ->
                _locationData.value = location
            }.addOnFailureListener {
                _locationData.value = null
            }
    }
}
