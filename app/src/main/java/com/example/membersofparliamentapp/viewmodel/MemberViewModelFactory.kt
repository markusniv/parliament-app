package com.example.membersofparliamentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.membersofparliamentapp.repository.MemberRepository

class MemberViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}