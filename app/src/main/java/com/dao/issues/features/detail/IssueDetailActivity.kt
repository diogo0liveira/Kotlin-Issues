package com.dao.issues.features.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.issues.Extras
import com.dao.issues.R
import com.dao.issues.base.BaseActivity
import com.dao.issues.base.OnCollectionChangedListener
import com.dao.issues.databinding.ActivityIssueDetailBinding
import com.dao.issues.databinding.ViewEmptyCommentsBinding
import com.dao.issues.databinding.ViewUserProfileBinding
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject

/**
 * Created in 27/03/19 10:53.
 *
 * @author Diogo Oliveira.
 */
class IssueDetailActivity : BaseActivity(), IssueDetailInteractor.View, View.OnClickListener, OnCollectionChangedListener
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
            helperEmpty.emptyVisibility = true
        }
        else
        {
            helper.contentDetail.contentCardComments.messageEmpty.viewStub!!.visibility = View.VISIBLE
            helperEmpty = DataBindingUtil.findBinding(helper.contentDetail.contentCardComments.messageEmpty.root)!!
            helperEmpty.emptyVisibility = true
        }

        adapter.setOnCollectionChangedListener(this)

        with(helper.contentDetail.contentCardComments.commentsList) {
            val divider = DividerItemDecoration(this@IssueDetailActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
            setHasFixedSize(true)

            adapter = this@IssueDetailActivity.adapter
        }

        helper.contentDetail.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            helper.appBar.isSelected = helper.contentDetail.scrollView.canScrollVertically(-1)
        }

        helper.buttonProfile.setOnClickListener(this)
        helper.buttonGithub.setOnClickListener(this)
    }

    override fun showLoading()
    {
        helperEmpty.emptyVisibility = false
        helperEmpty.progressVisibility = true
    }

    override fun hideLoading()
    {
        helperEmpty.progressVisibility = false
    }

    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.button_profile -> { executeRequireNetwork { presenter.loadUserProfile() } }
            R.id.button_github -> { presenter.showIssuesGithub() }
        }
    }

    override fun putOnForm(issue: Issue)
    {
        helper.issue = issue
        executeRequireNetwork { presenter.loadComments() }
    }

    override fun openInBrowser(url: String)
    {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun loadingUserProfile(user: User)
    {
        val view: ViewUserProfileBinding = ViewUserProfileBinding.inflate(layoutInflater)
        view.user = user

        val bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(view.root)
        bottomSheet.show()

        view.buttonGithub.setOnClickListener {
            openInBrowser(user.profileLink)
            bottomSheet.dismiss()
        }
    }

    override fun loadingComments(comments: List<Comment>)
    {
        adapter.setDataList(comments.toMutableList())
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
        helperEmpty.emptyVisibility = isEmpty
    }

    override fun executeRequireNetwork(block: () -> Unit)
    {
        if(isNetworkConnected())
        {
            removeNotifyDisconnected()
            block()
        }
        else
        {
            notifyDisconnected(helper.anchor) { executeRequireNetwork(block) }
        }
    }

    override fun toast(message: Int, duration: Int)
    {
        Toast.makeText(this, message, duration).show()
    }
}