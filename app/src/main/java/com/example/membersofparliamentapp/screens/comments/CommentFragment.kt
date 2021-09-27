package com.example.membersofparliamentapp.screens.comments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.adapters.CommentListAdapter
import com.example.membersofparliamentapp.adapters.MemberListAdapter
import com.example.membersofparliamentapp.databinding.FragmentCommentBinding
import com.example.membersofparliamentapp.screens.member_information.MemberInformationFragmentArgs
import com.example.membersofparliamentapp.screens.member_information.MemberInformationFragmentDirections
import com.example.membersofparliamentapp.viewmodel.CommentViewModel
import com.example.membersofparliamentapp.viewmodel.CommentViewModelFactory

private lateinit var binding: FragmentCommentBinding

class CommentFragment : Fragment() {

    private val args by navArgs<CommentFragmentArgs>()
    private val mCommentViewModel : CommentViewModel by viewModels {
        CommentViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)

        val adapter = CommentListAdapter()
        val recyclerView = binding.commentFragmentRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mCommentViewModel.getCommentsForMember(args.member.personNumber).observe(viewLifecycleOwner, { comment ->
            adapter.setData(comment)
        })

        setHasOptionsMenu(true)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.i("Current Member", args.member.pointsReceived.toString())
                    val action = CommentFragmentDirections.actionCommentFragmentToMemberInformationFragment(args.member)
                    findNavController().navigate(action)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddNote -> {
                val action = CommentFragmentDirections.actionCommentFragmentToAddCommentFragment(args.member)
                findNavController().navigate(action)
                true
            }
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> false
        }
        //return super.onOptionsItemSelected(item)
    }

}