package com.example.membersofparliamentapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.network.MemberApi

class MemberRepository(private val memberDao: MemberDao) {
    val readAllData: LiveData<List<Member>> = memberDao.readAllData()

    suspend fun addMember() {
        try {
            val memberListResult = MemberApi.retrofitService.getMembers()
            println(memberListResult)

            for (member in memberListResult) {
                memberDao.addMember(member)
            }
        } catch (e: Exception) {
            Log.i("Json call", "Failed misarably.")
        }
    }
}