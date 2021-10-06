package com.example.membersofparliamentapp.repository

import com.example.membersofparliamentapp.data.MemberDao
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.model.Member

/** 6.10.2021
 *
 */

class CommentRepository(private val memberDao: MemberDao) {

    fun getCommentsForMember(personNumber: Int) = memberDao.getMatchingComment(personNumber)

    suspend fun addComment(comment: Comment) = memberDao.addComment(comment)

    suspend fun deleteComment(comment: Comment) = memberDao.deleteComment(comment)

}