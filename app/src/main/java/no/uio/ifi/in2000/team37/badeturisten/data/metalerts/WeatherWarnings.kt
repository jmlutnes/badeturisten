package no.uio.ifi.in2000.team37.badeturisten.data.metalerts

data class WeatherWarning(
    val area: String,
    val description: String,
    val event: String,
    val severity: String,
    val instruction: String?,
    val eventEndingTime: String?,
    val status: String,
    val web: String?,
)