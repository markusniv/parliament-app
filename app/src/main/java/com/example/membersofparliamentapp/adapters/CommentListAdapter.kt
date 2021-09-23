package com.example.membersofparliamentapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.membersofparliamentapp.databinding.CommentViewBinding
import com.example.membersofparliamentapp.model.Comment

class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

    private var commentList = emptyList<Comment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = CommentViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: CommentViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(commentList[position]) {
                binding.singleListCommentText.text = this.content
            }
        }
    }

    override fun getItemCount(): Int = commentList.size

    fun setData(comment: List<Comment>) {
        this.commentList = comment
        notifyDataSetChanged()
    }



}