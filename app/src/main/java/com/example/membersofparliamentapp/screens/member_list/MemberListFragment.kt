package com.example.membersofparliamentapp.screens.member_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.adapters.MemberListAdapter
import com.example.membersofparliamentapp.databinding.FragmentMemberListBinding
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.viewmodel.MemberViewModel

private lateinit var binding: FragmentMemberListBinding

class MemberListFragment : Fragment() {

    private lateinit var mMemberViewModel : MemberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false)

        val adapter = MemberListAdapter()
        val recyclerView = binding.memberListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mMemberViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)
        mMemberViewModel.addMembers()
        mMemberViewModel.readAllData.observe(viewLifecycleOwner, { member ->
            adapter.setData(member)
        })


        return binding.root
    }

}