package com.example.membersofparliamentapp.repository

import androidx.lifecycle.LiveData
import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Member

class MemberRepository(private val memberDao: MemberDao) {
    val readAllData: LiveData<List<Member>> = memberDao.readAllData()

    suspend fun addMember(member: Member) {
        memberDao.addMember(member)
    }
}