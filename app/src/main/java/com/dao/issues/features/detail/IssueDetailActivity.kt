package com.dao.issues.features.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.issues.Extras
import com.dao.issues.R
import com.dao.issues.base.BaseActivity
import com.dao.issues.base.Recycler
import com.dao.issues.databinding.ActivityIssueDetailBinding
import com.dao.issues.databinding.ViewEmptyCommentsBinding
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import javax.inject.Inject

/**
 * Created in 27/03/19 10:53.
 *
 * @author Diogo Oliveira.
 */
class IssueDetailActivity : BaseActivity(), IssueDetailInteractor.View, Recycler.Adapter.OnCollectionChangedListener
{
    @Inject
    lateinit var presenter: IssueDetailInteractor.Presenter

    private lateinit var helperEmpty: ViewEmptyCommentsBinding
    private lateinit var helper: ActivityIssueDetailBinding

    private val adapter: CommentsAdapter by lazy {
        CommentsAdapter(this, mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_issue_detail)

        presenter.initialize(this)

        val issue: Issue = intent.getParcelableExtra(Extras.ISSUE)
        presenter.loadIssue(issue)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId)
        {
            android.R.id.home ->
            {
                finish()
                true
            }
            else ->
            {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        presenter.terminate()
    }

    override fun initializeView()
    {
        setSupportActionBar(helper.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        if(helper.contentDetail.contentCardComments.messageEmpty.isInflated)
        {
            helperEmpty.visible = true
        }
        else
        {
            with(helper.contentDetail.contentCardComments.messageEmpty) {
                viewStub!!.visibility = View.VISIBLE
                helperEmpty = DataBindingUtil.findBinding(this.root)!!
                helperEmpty.visible = true
            }
        }

        with(helper.contentDetail.contentCardComments.commentsList) {
            val divider = DividerItemDecoration(this@IssueDetailActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
            setHasFixedSize(true)
        }

        adapter.setOnCollectionChangedListener(this)
        helper.contentDetail.contentCardComments.commentsList.adapter = adapter

        helper.contentDetail.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            helper.appBar.isSelected = helper.contentDetail.scrollView.canScrollVertically(-1)
        }
    }

    override fun showLoading()
    {
//        helper.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading()
    {
//        helper.swipeRefresh.isRefreshing = false
    }

    override fun putOnForm(issue: Issue)
    {
        helper.issue = issue
        presenter.loadComments(issue)
    }

    override fun loadingComments(comments: List<Comment>)
    {
        adapter.setDataList(comments.toMutableList())
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
        helperEmpty.visible = isEmpty
    }

    override fun toast(message: Int, duration: Int)
    {
        Toast.makeText(this, message, duration).show()
    }
}