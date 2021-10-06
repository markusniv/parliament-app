package com.example.membersofparliamentapp

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.repository.CommentRepository
import com.example.membersofparliamentapp.repository.ScoreRepository

/**
 * The Application class for the app, giving access to the application context everywhere.
 */

class MyApp : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var appResources: Resources
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appResources = resources
    }
}