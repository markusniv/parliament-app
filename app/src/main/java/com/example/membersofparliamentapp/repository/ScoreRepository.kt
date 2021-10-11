package com.example.membersofparliamentapp.repository

import android.util.Log
import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Score
import com.example.membersofparliamentapp.network.MemberApi

/** (c) Markus Nivasalo, 6.10.2021
 *
 *      Repository class for the Score-table in the RoomDB
 */

class ScoreRepository(private val memberDao: MemberDao) {

    suspend fun updateScore(score: Score) = memberDao.updateScore(score)

    fun getCurrentScore(personNumber: Int) = memberDao.getCurrentScore(personNumber)

}