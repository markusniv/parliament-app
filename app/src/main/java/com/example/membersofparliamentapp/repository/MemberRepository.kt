package com.example.membersofparliamentapp.repository

import android.util.Log
import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Score
import com.example.membersofparliamentapp.network.MemberApi

/** (c) Markus Nivasalo, 16.9.2021
 *
 *      Repository class for the Member-table in the RoomDB
 */

class MemberRepository(private val memberDao: MemberDao) {

    // Function for adding members into the RoomDB. Initiates the Retrofit-call and if successful,
    // adds each member in the retrieved json-file into the database.
    suspend fun addMembers() {
        try {
            val memberListResult = MemberApi.retrofitService.getMembers()
            println(memberListResult)

            for (member in memberListResult) {
                /*
                This score below can be null, regardless of what the IDE says. When updating the list, we'll
                check if there isn't a score saved for this member and if so, we'll create it. This
                prevents scores from being wiped away every time the WorkManager downloads an updated
                member list.
                 */
                val score : Score? = memberDao.getScoreAsScore(member.personNumber)
                if (score == null) memberDao.addScore(Score(member.personNumber, 0))
                memberDao.addMember(member)
            }
        } catch (e: Exception) {
            Log.i("Json call", "Error: ${e}, Failed miserably.")
        }
    }

    fun readAllData() = memberDao.readAllData()

    fun filterByParty(party: String) = memberDao.filterByParty(party)

    fun filterByName(search: String) = memberDao.filterByName(search)

    fun filterByConstituency(constituency: String) = memberDao.filterByConstituency(constituency)


}
