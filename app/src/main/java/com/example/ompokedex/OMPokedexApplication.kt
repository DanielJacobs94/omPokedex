package com.example.ompokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OMPokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
