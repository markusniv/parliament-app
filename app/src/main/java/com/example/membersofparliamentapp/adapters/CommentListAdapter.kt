package com.example.membersofparliamentapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.membersofparliamentapp.MyApp
import com.example.membersofparliamentapp.R
import com.example.membersofparliamentapp.databinding.CommentViewBinding
import com.example.membersofparliamentapp.model.Comment

/**     (c) Markus Nivasalo, 23.9.2021
 *
 *      RecyclerView Adapter -class for the CommentFragment, showing the list of all the comments
 *      made for the currently selected parliament member.
 */

class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

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
                setUnselectedView(binding.singleListComment, this)
                binding.singleListCommentText.text = this.content
                binding.singleListComment.setOnLongClickListener {
                    setSelectedView(it, this)
                    true
                }
                binding.singleListComment.setOnClickListener {
                    if (this in selectedCommentsList) setUnselectedView(it, this)
                    else if (this !in selectedCommentsList && selectedCommentsList.isNotEmpty()) {
                        setSelectedView(it, this)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = commentList.size

    fun setData(comment: List<Comment>) {
        this.commentList = comment
        notifyDataSetChanged()
    }

    /*
        The functions below deal with selecting and unselecting comments in the Comment-fragment,
        so that the fragment knows whether or not we are in the comment deleting mode
    */

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun addSelectedComment(comment: Comment) {
        selectedCommentsLiveData.value?.add(comment)
        selectedCommentsList.add(comment)
        selectedCommentsLiveData.notifyObserver()
    }

    private fun removeSelectedComment(comment: Comment) {
        selectedCommentsLiveData.value?.remove(comment)
        selectedCommentsList.remove(comment)
        selectedCommentsLiveData.notifyObserver()
    }

    fun emptySelectedCommentsList() {
        selectedCommentsLiveData.value?.clear()
        selectedCommentsList.clear()
        selectedCommentsLiveData.notifyObserver()
    }

    private fun setSelectedView(view : View, comment: Comment) {
        view.setBackgroundColor(ContextCompat.getColor(MyApp.appContext, R.color.lighter_blue))
        view.background.alpha = 20
        addSelectedComment(comment)
    }

    private fun setUnselectedView(view : View, comment: Comment) {
        view.setBackgroundColor(ContextCompat.getColor(MyApp.appContext, R.color.white))
        removeSelectedComment(comment)
    }


}