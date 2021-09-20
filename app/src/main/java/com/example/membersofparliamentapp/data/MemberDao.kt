package com.example.membersofparliamentapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.membersofparliamentapp.model.Member

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMember(member : Member)

    @Query("SELECT * FROM member_table ORDER BY personNumber")
    fun readAllData(): LiveData<List<Member>>

    @Query("SELECT COUNT(*) from member_table")
    suspend fun getMemberCount() : Int

}
