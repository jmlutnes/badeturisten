package no.uio.ifi.in2000.team37.badeturisten

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class BadeturistenApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Log.e("UncaughtException", "Uncaught exception in thread: ${thread.name}", e)
        }
    }
}