package com.dao.issues.features.detail

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dao.issues.R
import com.dao.issues.base.Recycler
import com.dao.issues.databinding.ViewRowCommentBinding
import com.dao.issues.model.Comment

/**
 * Created in 27/03/19 22:56.
 *
 * @author Diogo Oliveira.
 */
class CommentsAdapter(
        context: Context,
        list: MutableList<Comment>) :
        Recycler.Adapter<Comment, CommentsAdapter.ViewHolder>(context, list)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ViewRowCommentBinding = this.inflate(parent, R.layout.view_row_comment)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Comment)
    {
        holder.binding.comment = item
    }

    inner class ViewHolder(val binding: ViewRowCommentBinding) : RecyclerView.ViewHolder(binding.root)
}