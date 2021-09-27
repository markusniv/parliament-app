package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.Filter
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberListViewModel : ViewModel() {

    var currentFilter: Filter

    private val repository: MemberRepository

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        repository = MemberRepository(memberDao)
        currentFilter = Filter()
    }

    fun addMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMembers()
        }
    }

    fun readAllData() = repository.readAllData()

    fun readAllDataByParty() = repository.readAllDataByParty()

    fun filterByParty(party: String) = repository.filterByParty(party)

    fun filterByName(search: String) = repository.filterByName(search)
}

class MemberListViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}