package com.dao.issues.features.issues

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dao.issues.R
import com.dao.issues.base.paging.Recycler
import com.dao.issues.databinding.ViewRowIssueBinding
import com.dao.issues.model.Issue

/**
 * Created in 26/03/19 21:55.
 *
 * @author Diogo Oliveira.
 */
class IssuesAdapter(
        context: Context,
        list: MutableList<Issue>,
        private val listener: IssueViewOnClickListener) :
        Recycler.Adapter<Issue, IssuesAdapter.ViewHolder>(context, list, COMPARATOR)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ViewRowIssueBinding = this.inflate(parent, R.layout.view_row_issue)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Issue?)
    {
        holder.binding.issue = item
    }

    inner class ViewHolder(val binding: ViewRowIssueBinding, private val listener: IssueViewOnClickListener) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener
    {
        init
        {
            this.itemView.setOnClickListener(this)
        }

        override fun onClick(view: View)
        {
            listener.onIssueViewOnClick(binding.issue!!)
        }

    }

    companion object
    {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Issue>()
        {
            override fun areItemsTheSame(oldItem: Issue, newItem: Issue) = (oldItem.number == newItem.number)
            override fun areContentsTheSame(oldItem: Issue, newItem: Issue) = (oldItem == newItem)
        }
    }

    interface IssueViewOnClickListener
    {
        fun onIssueViewOnClick(issue: Issue)
    }
}