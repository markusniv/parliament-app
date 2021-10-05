package com.example.membersofparliamentapp.screens.comments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.adapters.CommentListAdapter
import com.example.membersofparliamentapp.adapters.MemberListAdapter
import com.example.membersofparliamentapp.data.MemberDatabase
import com.example.membersofparliamentapp.databinding.FragmentCommentBinding
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.repository.MemberRepository
import com.example.membersofparliamentapp.screens.member_information.MemberInformationFragmentArgs
import com.example.membersofparliamentapp.screens.member_information.MemberInformationFragmentDirections
import com.example.membersofparliamentapp.viewmodel.CommentViewModel
import com.example.membersofparliamentapp.viewmodel.CommentViewModelFactory

/** (c) Markus Nivasalo, 16.9.2021
 *
 *      The fragment that displays once the user clicks the comment menu button in
 *      MemberInformationFragment. Includes a RecyclerView of comments left for the current member.
 *      The user can long click on a comment to start choosing comments for possible deletion. The
 *      default menu button takes the user to AddCommentFragment.
 */

private lateinit var binding: FragmentCommentBinding
private lateinit var adapter: CommentListAdapter

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

        adapter = mCommentViewModel.adapter
        val recyclerView = binding.commentFragmentRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mCommentViewModel.getCommentsForMember(args.member.personNumber).observe(viewLifecycleOwner, { comment ->
            adapter.setData(comment)
        })

        mCommentViewModel.getSelectedCommentsLiveData().observe(viewLifecycleOwner, {
            mCommentViewModel.commentsSelected = mCommentViewModel.getSelectedCommentsList().size > 0
            setActionBarTitle()
            requireActivity().invalidateOptionsMenu()
        })

        setHasOptionsMenu(true)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val action = CommentFragmentDirections.actionCommentFragmentToMemberInformationFragment(args.member)
                    findNavController().navigate(action)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!mCommentViewModel.commentsSelected) {
            inflater.inflate(R.menu.note_add, menu)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            inflater.inflate(R.menu.delete_menu, menu)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddNote -> {
                val action = CommentFragmentDirections.actionCommentFragmentToAddCommentFragment(args.member)
                findNavController().navigate(action)
                true
            }
            R.id.menuDelete -> {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("Are you sure you wish to delete the selected comments?")
                builder.setTitle("Confirm")
                builder.setCancelable(true)
                builder.setPositiveButton("Yes") { _, _ ->
                    deleteComments()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                builder.show()
                true
            }
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> false
        }
    }

    private fun deleteComments() {
        mCommentViewModel.commentsSelected = false
        setActionBarTitle()
        requireActivity().invalidateOptionsMenu()
        for (comment in mCommentViewModel.getSelectedCommentsList()) {
            mCommentViewModel.deleteComment(comment)
        }
        mCommentViewModel.emptySelectedCommentsList()
    }

    private fun setActionBarTitle() {
        if (mCommentViewModel.commentsSelected) {
            (activity as AppCompatActivity).supportActionBar?.title = "Delete comments"
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Comments"
        }
    }

}