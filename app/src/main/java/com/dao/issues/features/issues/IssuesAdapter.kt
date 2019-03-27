package com.dao.issues.features.issues

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dao.issues.R
import com.dao.issues.base.Recycler
import com.dao.issues.databinding.ViewRowIssuesBinding
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
        Recycler.Adapter<Issue, IssuesAdapter.ViewHolder>(context, list)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding: ViewRowIssuesBinding = this.inflate(parent, R.layout.view_row_issues)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Issue)
    {
        holder.binding.issue = item
    }

    inner class ViewHolder(val binding: ViewRowIssuesBinding, private val listener: IssueViewOnClickListener) :
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

    interface IssueViewOnClickListener
    {
        fun onIssueViewOnClick(issue: Issue)
    }
}