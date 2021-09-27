package com.example.membersofparliamentapp.screens.member_information

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.FragmentMemberInformationBinding
import com.example.membersofparliamentapp.functions.getPartyColor
import com.example.membersofparliamentapp.functions.getPartyName
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.viewmodel.MemberInformationViewModel
import com.example.membersofparliamentapp.viewmodel.MemberInformationViewModelFactory
import com.squareup.picasso.Picasso

private lateinit var binding: FragmentMemberInformationBinding
private var currentPoints: Int = 0

private lateinit var currentMember : Member

class MemberInformationFragment : Fragment() {

    private val args by navArgs<MemberInformationFragmentArgs>()
    private val mMemberInformationViewModel : MemberInformationViewModel by viewModels {
        MemberInformationViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_information, container, false)

        currentMember = args.member

        setHasOptionsMenu(true)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_memberInformationFragment_to_memberListFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        currentPoints = currentMember.pointsReceived

        getMember()

        binding.deductPoints.setOnClickListener {
            updatePoints(-1)
        }
        binding.addPoints.setOnClickListener {
            updatePoints(1)
        }

        return binding.root
    }

    // Get a member from the ParliamentMembersData object and edit the fragment views accordingly
    private fun getMember() {
        val memb = currentMember
        binding.txtTitle.text = getMinistry(memb)
        Picasso.get().load("https://avoindata.eduskunta.fi/${memb.picture}").into(binding.imgMember)
        //binding.imgParty.setImageResource(getLogo(memb))
        binding.partyText.text = getPartyName(memb)
        binding.partyText.setTextColor(resources.getColor(getPartyColor(memb)))
        binding.txtName.text = ("${memb.first} ${memb.last}")
        binding.txtAge.text = (2021 - memb.bornYear).toString() + " years old"
        binding.txtConstituency.text = memb.constituency
        binding.pointView.text = currentPoints.toString()
    }

    // Get a drawable id of a member's party's logo
    private fun getLogo(member : Member) : Int {
        return when(member.party) {
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

    private fun updatePoints(amount: Int) {
        currentPoints += amount
        binding.pointView.text = currentPoints.toString()
    }

    private fun updateDatabase(member: Member) {
        mMemberInformationViewModel.updatePoints(member)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuNotes -> {
                updatePoints()
                val action = MemberInformationFragmentDirections.actionMemberInformationFragmentToCommentFragment(getTempMember(currentMember))
                findNavController().navigate(action)
            }
            android.R.id.home -> {
                updatePoints()
                requireActivity().onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTempMember(member: Member): Member {
        return Member(
            member.personNumber,
            member.seatNumber,
            member.last,
            member.first,
            member.party,
            member.minister,
            member.picture,
            member.twitter,
            member.bornYear,
            member.constituency,
            currentPoints
        )
    }

    private fun updatePoints() {
        updateDatabase(getTempMember(currentMember))
    }
}