package com.example.membersofparliamentapp.repository

import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.model.Member

/** (c) Markus Nivasalo, 6.10.2021
 *
 *      Repository class for the Comment-table in the RoomDB
 */

class CommentRepository(private val memberDao: MemberDao) {

    fun getCommentsForMember(personNumber: Int) = memberDao.getMatchingComment(personNumber)

    suspend fun addComment(comment: Comment) = memberDao.addComment(comment)

    suspend fun deleteComment(comment: Comment) = memberDao.deleteComment(comment)

}