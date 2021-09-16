package com.example.membersofparliamentapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.membersofparliamentapp.databinding.FragmentMemberListBinding
import com.example.membersofparliamentapp.databinding.MemberViewBinding
import com.example.membersofparliamentapp.model.Member

class MemberListAdapter : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    private var memberList = emptyList<Member>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = MemberViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: MemberViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(memberList[position]) {
                binding.singleListMemberParty.text = this.party
                binding.singleListMemberName.text = "${this.first} ${this.last}"
            }
        }
    }

    override fun getItemCount(): Int = memberList.size

    fun setData(member: List<Member>) {
        this.memberList = member
        notifyDataSetChanged()
    }



}