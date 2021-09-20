package com.example.membersofparliamentapp.screens.member_information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.FragmentMemberInformationBinding
import com.example.membersofparliamentapp.databinding.FragmentMemberInformationBindingImpl
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.viewmodel.MemberViewModel
import com.example.membersofparliamentapp.viewmodel.MemberViewModelFactory

private lateinit var binding: FragmentMemberInformationBinding

class MemberInformationFragment : Fragment() {

    private val args by navArgs<MemberInformationFragmentArgs>()
    private val mMemberViewModel : MemberViewModel by viewModels {
        MemberViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_information, container, false)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_memberInformationFragment_to_memberListFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        getMember()

        return binding.root
    }

    // Get a member from the ParliamentMembersData object and edit the fragment views accordingly
    private fun getMember() {
        val memb = args.member
        binding.txtTitle.text = getMinistry(memb)
        binding.imgParty.setImageResource(getLogo(memb))
        binding.txtName.text = ("${memb.first} ${memb.last}")
        binding.txtAge.text = (2021 - memb.bornYear).toString() + " years old"
        binding.txtConstituency.text = memb.constituency
    }

    // Get a drawable id of a member's party's logo
    private fun getLogo(memb : Member) : Int {
        return when(memb.party) {
            "kd" -> R.drawable.ic_kd
            "kesk" -> R.drawable.ic_kesk
            "kok" -> R.drawable.ic_kok
            "liik" -> R.drawable.ic_liik
            "ps" -> R.drawable.ic_ps
            "r" -> R.drawable.ic_rkp
            "sd" -> R.drawable.ic_sdp
            "vihr" -> R.drawable.ic_vihr
            else -> R.drawable.ic_vas
        }
    }
    // Check if the member is a minister and return a string accordingly
    private fun getMinistry(memb : Member) : String = if (memb.minister) "Minister" else "Member of parliament"

}