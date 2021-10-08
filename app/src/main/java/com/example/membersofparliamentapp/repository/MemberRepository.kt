package com.example.membersofparliamentapp.repository

import android.util.Log
import com.example.membersofparliamentapp.data.MemberDao
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
                memberDao.addMember(member)
            }
        } catch (e: Exception) {
            Log.i("Json call", "Error: ${e}, Failed misarably.")
        }
    }

    fun readAllData() = memberDao.readAllData()

    fun readAllDataByParty() = memberDao.readAllDataByParty()

    fun filterByParty(party: String) = memberDao.filterByParty(party)

    fun filterByName(search: String) = memberDao.filterByName(search)


}
