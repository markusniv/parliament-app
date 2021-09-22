package com.example.membersofparliamentapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.membersofparliamentapp.model.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMember(member : Member)

    @Query("SELECT * FROM member_table")
    fun readAllData(): LiveData<List<Member>>

    @Query("SELECT COUNT(*) from member_table")
    suspend fun getMemberCount() : Int

    @Query("SELECT * FROM member_table ORDER BY party")
    fun readAllDataByParty(): LiveData<List<Member>>

    @Query("SELECT * FROM member_table WHERE party like :party")
    fun filterByParty(party : String) : LiveData<List<Member>>

    @Update
    suspend fun updatePoints(member: Member)

}
