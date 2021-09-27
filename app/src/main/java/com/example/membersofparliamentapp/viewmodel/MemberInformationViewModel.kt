package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberInformationViewModel : ViewModel() {
    private val repository: MemberRepository

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        repository = MemberRepository(memberDao)
    }

    fun updatePoints(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePoints(member)
        }
    }
}

class MemberInformationViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberInformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberInformationViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}