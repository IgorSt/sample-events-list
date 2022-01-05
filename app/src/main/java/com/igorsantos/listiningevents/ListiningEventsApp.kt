package com.igorsantos.listiningevents

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ListiningEventsApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
           Timber.plant(Timber.DebugTree())
        }
    }
}