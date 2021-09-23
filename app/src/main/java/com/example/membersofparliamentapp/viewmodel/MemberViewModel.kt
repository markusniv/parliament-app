package com.example.membersofparliamentapp.viewmodel

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.*
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.Filter
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.network.MemberApi
import com.example.membersofparliamentapp.repository.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlin.math.log

class MemberViewModel : ViewModel() {

    val readAllDataByParty: LiveData<List<Member>>
    var currentFilter: Filter

    private val repository: MemberRepository

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        repository = MemberRepository(memberDao)
        readAllDataByParty = repository.readAllDataByParty
        currentFilter = Filter()

    }

    fun addMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMembers()
        }
    }

    fun updatePoints(member: Member) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePoints(member)
        }
    }

    fun readAllData() = repository.readAllData()

    fun filterByParty(party: String) = repository.filterByParty(party)

    fun filterByName(search: String) = repository.filterByName(search)

}
