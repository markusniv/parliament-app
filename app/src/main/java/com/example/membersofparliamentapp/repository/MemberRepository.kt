package com.example.membersofparliamentapp.repository

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.network.MemberApi

class MemberRepository(private val memberDao: MemberDao) {
    val readAllData: LiveData<List<Member>> = memberDao.readAllData()
    var memberCount: Int = 0

    suspend fun addMembers() {
        memberCount = memberDao.getMemberCount()
        Log.i("Member count", "Member count is: $memberCount")
        if (memberCount == 0) {
            Log.i("Database pull check", "There wasn't any members available!")
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

    suspend fun updatePoints(member: Member) {
        memberDao.updatePoints(member)
    }

}
