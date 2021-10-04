package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.Filter
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {
    private val repository: MemberRepository

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        repository = MemberRepository(memberDao)
    }

    fun getCommentsForMember(personNumber: Int) = repository.getCommentsForMember(personNumber)

    fun deleteComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteComment(comment)
        }
    }
}

class CommentViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}