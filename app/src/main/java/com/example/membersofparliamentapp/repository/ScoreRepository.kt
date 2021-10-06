package com.example.membersofparliamentapp.repository

import android.util.Log
import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.model.Score
import com.example.membersofparliamentapp.network.MemberApi

/** 6.10.2021
 *
 */

class ScoreRepository(private val memberDao: MemberDao) {

    suspend fun updateScore(score: Score) = memberDao.updateScore(score)

    suspend fun addScores() {
        try {
            val memberListResult = MemberApi.retrofitService.getMembers()

            for (member in memberListResult) {
                val score = Score(member.personNumber, 0)
                memberDao.addScore(score)
            }
        } catch (e: Exception) {
            Log.i("Json call", "Failed misarably.")
        }
    }

    suspend fun getScoreCount() = memberDao.getScoreCount()

    fun getCurrentScore(personNumber: Int) = memberDao.getCurrentScore(personNumber)

}