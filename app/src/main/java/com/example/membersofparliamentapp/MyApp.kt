package com.example.membersofparliamentapp

import android.app.Application
import android.content.Context

/**
 * The Application class for the app, giving access to the application context everywhere.
 */

class MyApp : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}