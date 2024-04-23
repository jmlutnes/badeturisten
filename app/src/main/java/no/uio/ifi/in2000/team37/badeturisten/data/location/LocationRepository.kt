import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationRepository(private val context: Context) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val _locationData = MutableStateFlow<Location?>(null)

    val locationData = _locationData.asStateFlow()

    fun fetchLastLocation() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, handle this case
            _locationData.value = null
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            _locationData.value = location
        }.addOnFailureListener {
            _locationData.value = null
        }
    }
}
