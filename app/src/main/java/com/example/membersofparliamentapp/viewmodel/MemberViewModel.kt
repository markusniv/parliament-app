package com.example.membersofparliamentapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.network.MemberApi
import com.example.membersofparliamentapp.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MemberViewModel (application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Member>>
    private val repository: MemberRepository

    init {
        val memberDao = MemberDatabase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        readAllData = repository.readAllData
    }

    fun addMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMember()
        }
    }

}