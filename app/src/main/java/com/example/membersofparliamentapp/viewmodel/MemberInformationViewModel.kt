package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.model.Score
import com.example.membersofparliamentapp.repository.MemberRepository
import com.example.membersofparliamentapp.repository.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**     (c) Markus Nivasalo, 27.9.2021
 *
 *      ViewModel-class for the MemberInformationFragment.
 */


class MemberInformationViewModel : ViewModel() {
    private val memberRepository: MemberRepository
    private val scoreRepository: ScoreRepository

    init {
        val memberDao = MemberDatabase.getDatabase(MyApp.appContext).memberDao()
        memberRepository = MemberRepository(memberDao)
        scoreRepository = ScoreRepository(memberDao)
    }

    fun updatePoints(score: Score) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.updateScore(score)
        }
    }

    fun getCurrentScore(personNumber: Int) = scoreRepository.getCurrentScore(personNumber)

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