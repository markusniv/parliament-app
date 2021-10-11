package com.example.membersofparliamentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.network.DownloadWorker
import com.example.membersofparliamentapp.repository.CommentRepository
import com.example.membersofparliamentapp.repository.ScoreRepository
import com.example.membersofparliamentapp.screens.member_information.MemberInformationFragment
import java.util.concurrent.TimeUnit

/**     (c) Markus Nivasalo, 16.9.2021
 *
 *      MainActivity-class for the app, has the PeriodicWorkRequest for updating the member database,
 *      and also overrides the back arrow function for coherent moving across fragments.
 */

class MainActivity : AppCompatActivity() {

    private val periodicWorkRequest = PeriodicWorkRequestBuilder<DownloadWorker>(24, TimeUnit.HOURS).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WorkManager.getInstance(MyApp.appContext).enqueueUniquePeriodicWork("updateMembers", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)

        setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}