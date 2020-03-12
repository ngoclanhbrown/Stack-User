package com.brown.stackuser

import android.app.Application
import timber.log.Timber

class StackUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}
