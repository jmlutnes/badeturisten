package no.uio.ifi.in2000.team37.badeturisten.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {
     /*
     Sjekker om det er ett koblet nettverk
     returnerer en boolean som sier om nettverk er koblet til eller ikke
     sjekk med activenetwork om det er ett aktivt nettverk, hvis ikke returner false med engang
     sjekk med capabilities om det aktive nettverket har kapasitet hvis ikke returner false
     den bruker connectivitymanager for å vurdere om det aktive internettet har kapasitet
     sjekker om det aktive nettverket har kapasitet på gi tilgang til internett
     returnerer true hvis nettverket er i stand til det, ellers returner false
     */

    fun isNetworkAvail(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}