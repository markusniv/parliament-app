package com.example.membersofparliamentapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.MemberViewBinding
import com.example.membersofparliamentapp.functions.getPartyColor
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.screens.member_list.MemberListFragmentDirections

/**     (c) Markus Nivasalo, 16.9.2021
 *
 *      RecyclerView Adapter -class for the MemberListFragment, showing the list of all the parliament
 *      members.
 */

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
                binding.singleListMemberParty.setTextColor(
                    getColor(MyApp.appContext, getPartyColor(this)))
                binding.singleListMemberName.text = MyApp.appResources.getString(R.string.member_whole_name, this.first, this.last)
                binding.singleListMember.setOnClickListener {
                    val action = MemberListFragmentDirections.actionMemberListFragmentToMemberInformationFragment(this)
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int = memberList.size

    fun setData(member: List<Member>) {
        this.memberList = member
        notifyDataSetChanged()
    }
}