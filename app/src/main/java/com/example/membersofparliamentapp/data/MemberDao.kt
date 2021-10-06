package com.example.membersofparliamentapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.model.Score
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    // Member SQL queries

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

    @Query("SELECT * FROM member_table WHERE first like :search OR last LIKE :search")
    fun filterByName(search : String) : LiveData<List<Member>>

    // Comment SQL queries

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment : Comment)

    @Query("SELECT * FROM comment_table")
    fun readAllComments(): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_table WHERE personNumber LIKE :personNumber")
    fun getMatchingComment(personNumber : Int): LiveData<List<Comment>>

    @Delete
    suspend fun deleteComment(comment: Comment)

    // Score SQL queries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScore(score: Score)

    @Update
    suspend fun updateScore(score: Score)

    @Query("SELECT COUNT(*) from score_table")
    suspend fun getScoreCount() : Int

    @Query("SELECT * FROM score_table WHERE personNumber like :personNumber")
    fun getCurrentScore(personNumber: Int): LiveData<Score>

}
