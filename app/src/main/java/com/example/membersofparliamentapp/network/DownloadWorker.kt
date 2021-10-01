package com.example.membersofparliamentapp.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.repository.MemberRepository

class DownloadWorker(appContext: Context, workerParams: WorkerParameters) :
        CoroutineWorker(appContext, workerParams) {

    val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
    private val repository: MemberRepository = MemberRepository(memberDao)

    override suspend fun doWork(): Result {
        repository.addMembers()
        return Result.success()
    }

}