package com.example.membersofparliamentapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.Filter
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Score
import com.example.membersofparliamentapp.repository.MemberRepository
import com.example.membersofparliamentapp.repository.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberListViewModel : ViewModel() {

    var currentFilter: Filter
    private val memberRepository: MemberRepository
    private val scoreRepository: ScoreRepository

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        memberRepository = MemberRepository(memberDao)
        scoreRepository = ScoreRepository(memberDao)
        currentFilter = Filter()

    }

    fun addScores() {
        viewModelScope.launch(Dispatchers.IO) {
            val scoreAmount = scoreRepository.getScoreCount()
            if (scoreAmount == 0) {
                scoreRepository.addScores()
            }
        }
    }


    fun readAllData() = memberRepository.readAllData()

    fun readAllDataByParty() = memberRepository.readAllDataByParty()

    fun filterByParty(party: String) = memberRepository.filterByParty(party)

    fun filterByName(search: String) = memberRepository.filterByName(search)
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