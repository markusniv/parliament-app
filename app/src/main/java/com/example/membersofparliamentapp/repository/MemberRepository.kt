package com.example.membersofparliamentapp.repository

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.constraintlayout.helper.widget.Flow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.network.MemberApi

class MemberRepository(private val memberDao: MemberDao) {
    suspend fun addMembers() {
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

    // Member functions

    fun readAllData() = memberDao.readAllData()

    fun readAllDataByParty() = memberDao.readAllDataByParty()

    fun filterByParty(party: String) = memberDao.filterByParty(party)

    fun filterByName(search: String) = memberDao.filterByName(search)

    suspend fun updatePoints(member: Member) = memberDao.updatePoints(member)

    // Comment functions

    fun getCommentsForMember(personNumber: Int) = memberDao.getMatchingComment(personNumber)

    suspend fun addComment(comment: Comment) = memberDao.addComment(comment)

    suspend fun deleteComment(comment: Comment) = memberDao.deleteComment(comment)

}
