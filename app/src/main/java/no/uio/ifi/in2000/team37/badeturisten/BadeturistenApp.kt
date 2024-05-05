package no.uio.ifi.in2000.team37.badeturisten

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BadeturistenApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Log.e("UncaughtException", "Uncaught exception in thread: ${thread.name}", e)
        }
    }
}