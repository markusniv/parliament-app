package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.repository.CommentRepository
import com.example.membersofparliamentapp.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**     (c) Markus Nivasalo, 27.9.2021
 *
 *      ViewModel-class for the AddCommentFragment.
 */

class AddCommentViewModel : ViewModel() {
    private val repository: CommentRepository

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        repository = CommentRepository(memberDao)
    }

    fun addComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addComment(comment)
        }
    }

}

class AddCommentViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddCommentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}