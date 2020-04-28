package com.example.remotecontrolcar

import android.app.Application
import timber.log.Timber

class LifecycleChecker: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}