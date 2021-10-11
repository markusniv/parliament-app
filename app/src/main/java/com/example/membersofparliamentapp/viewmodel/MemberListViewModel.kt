package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.Filter
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.repository.MemberRepository
import com.example.membersofparliamentapp.repository.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**     (c) Markus Nivasalo, 27.9.2021
 *
 *      ViewModel-class for the MemberListFragment.
 */

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

    fun readAllData() = memberRepository.readAllData()

    fun filterByParty(party: String) = memberRepository.filterByParty(party)

    fun filterByName(search: String) = memberRepository.filterByName(search)

    fun filterByConstituency(constituency: String) = memberRepository.filterByConstituency(constituency)
}

class MemberListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}