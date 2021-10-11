package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.adapters.CommentListAdapter
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**     (c) Markus Nivasalo, 27.9.2021
 *
 *      ViewModel-class for the CommentFragment.
 */


class CommentViewModel : ViewModel() {
    private val repository: CommentRepository
    var adapter = CommentListAdapter()
    var commentsSelected : Boolean = false

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        repository = CommentRepository(memberDao)
    }

    fun getCommentsForMember(personNumber: Int) = repository.getCommentsForMember(personNumber)

    fun getSelectedCommentsLiveData() = adapter.selectedCommentsLiveData

    fun getSelectedCommentsList() = adapter.selectedCommentsList

    fun deleteComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteComment(comment)
        }
    }

    fun emptySelectedCommentsList() = adapter.emptySelectedCommentsList()
}

class CommentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}