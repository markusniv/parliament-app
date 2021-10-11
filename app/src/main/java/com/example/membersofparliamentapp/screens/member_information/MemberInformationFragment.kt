package com.example.membersofparliamentapp.screens.member_information

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.FragmentMemberInformationBinding
import com.example.membersofparliamentapp.functions.getPartyColor
import com.example.membersofparliamentapp.functions.getPartyName
import com.example.membersofparliamentapp.model.Member
import com.example.membersofparliamentapp.model.Score
import com.example.membersofparliamentapp.viewmodel.MemberInformationViewModel
import com.example.membersofparliamentapp.viewmodel.MemberInformationViewModelFactory
import com.squareup.picasso.Picasso

/**     (c) Markus Nivasalo, 16.9.2021
 *
 *      The fragment that displays once the user clicks on a mp in MemberListFragment. Shows basic
 *      information of the mp, including name, party, age and constituency. Also allows the user
 *      to add or reduce points from the member, saving this rating into the RoomDB. Menu button
 *      allows access to the CommentFragment.
 */

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

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.memberImageParty.visibility = View.GONE
        }
        currentMember = args.member
        mMemberInformationViewModel.getCurrentScore(currentMember.personNumber).observe(viewLifecycleOwner, {
            currentPoints = it.score
            updateUi()
        })

        setHasOptionsMenu(true)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_memberInformationFragment_to_memberListFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

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
        binding.partyText.text = getPartyName(memb)
        binding.partyText.setTextColor(ContextCompat.getColor(MyApp.appContext, getPartyColor(memb)))
        binding.txtName.text = ("${memb.first} ${memb.last}")
        binding.txtAge.text = getString(R.string.years_old, (2021 - memb.bornYear))
        binding.txtConstituency.text = memb.constituency
        binding.pointView.text = currentPoints.toString()
    }

    // Check if the member is a minister and return a string accordingly
    private fun getMinistry(memb : Member) : String = if (memb.minister) "Minister" else "Member of parliament"

    private fun updatePoints(amount: Int) {
        currentPoints += amount
        updateUi()
    }

    private fun updateDatabase(score: Score) {
        mMemberInformationViewModel.updatePoints(score)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuNotes -> {
                val score = Score(currentMember.personNumber, currentPoints)
                Log.i("score", score.toString())
                updateDatabase(score)
                val action = MemberInformationFragmentDirections.actionMemberInformationFragmentToCommentFragment(currentMember)
                findNavController().navigate(action)
            }
            android.R.id.home -> {
                val score = Score(currentMember.personNumber, currentPoints)
                updateDatabase(score)
                requireActivity().onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUi() {
        binding.pointView.text = currentPoints.toString()
    }

    override fun onPause() {
        updateDatabase(Score(currentMember.personNumber, currentPoints))
        super.onPause()
    }

}