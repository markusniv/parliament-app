package com.example.membersofparliamentapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.CommentViewBinding
import com.example.membersofparliamentapp.model.Comment
import com.example.membersofparliamentapp.viewmodel.CommentViewModel

class CommentListAdapter() : RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

    private var commentList = emptyList<Comment>()
    val selectedCommentsLiveData = MutableLiveData<MutableList<Comment>>()
    val selectedCommentsList = mutableListOf<Comment>()

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
                binding.singleListComment.setOnLongClickListener {
                    it.setBackgroundColor(MyApp.appResources.getColor(R.color.design_default_color_primary))
                    it.background.alpha = 20
                    addSelectedComment(this)
                    true
                }
                binding.singleListComment.setOnClickListener {
                    it.setBackgroundColor(MyApp.appResources.getColor(R.color.cardview_light_background))
                    removeSelectedComment(this)
                }
                TODO("Make it so you only have to long click once and then can simply click the rest.")
            }
        }
    }

    override fun getItemCount(): Int = commentList.size

    fun setData(comment: List<Comment>) {
        this.commentList = comment
        notifyDataSetChanged()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun addSelectedComment(comment: Comment) {
        selectedCommentsLiveData.value?.add(comment)
        selectedCommentsList.add(comment)
        selectedCommentsLiveData.notifyObserver()
    }

    fun removeSelectedComment(comment: Comment) {
        selectedCommentsLiveData.value?.remove(comment)
        selectedCommentsList.remove(comment)
        selectedCommentsLiveData.notifyObserver()
    }

    fun emptySelectedCommentsList() {
        selectedCommentsLiveData.value?.clear()
        selectedCommentsList.clear()
    }

}