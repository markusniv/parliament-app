package com.example.membersofparliamentapp.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.repository.MemberRepository

/** (c) Markus Nivasalo, 1.10.2021
 *
 *      Worker class handling the retrieval of the members from the backend.
 */

class DownloadWorker(appContext: Context, workerParams: WorkerParameters) :
        CoroutineWorker(appContext, workerParams) {

    val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
    private val repository: MemberRepository = MemberRepository(memberDao)

    override suspend fun doWork(): Result {
        repository.addMembers()
        return Result.success()
    }

}