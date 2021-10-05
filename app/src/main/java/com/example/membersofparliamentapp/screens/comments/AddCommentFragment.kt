package com.example.membersofparliamentapp.screens.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.FragmentAddCommentBinding
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.screens.member_information.MemberInformationFragmentArgs
import com.example.membersofparliamentapp.viewmodel.AddCommentViewModel
import com.example.membersofparliamentapp.viewmodel.AddCommentViewModelFactory

/** (c) Markus Nivasalo, 16.9.2021
 *
 *      The fragment that displays once the user clicks on the Add comment -menu button in the
 *      CommentFragment. Allows the user to add a comment which will then be saved to the RoomDB
 *      comment table.
 */

private lateinit var binding: FragmentAddCommentBinding

class AddCommentFragment : Fragment() {

    private val args by navArgs<AddCommentFragmentArgs>()
    private val mAddCommentViewModel : AddCommentViewModel by viewModels {
        AddCommentViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_comment, container, false)

        binding.addCommentButton.setOnClickListener {
            val commentText = binding.editTextComment.text.toString()
            val commentPersonNumber = args.member.personNumber
            if (commentText.isNotEmpty()) {
                mAddCommentViewModel.addComment(Comment(0, commentPersonNumber, commentText))
                val action = AddCommentFragmentDirections.actionAddCommentFragmentToCommentFragment(args.member)
                findNavController().navigate(action)
            } else {
                Toast.makeText(MyApp.appContext, "Comment cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}