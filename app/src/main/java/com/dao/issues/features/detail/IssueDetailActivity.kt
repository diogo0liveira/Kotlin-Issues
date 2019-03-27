package com.dao.issues.features.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dao.issues.Extras
import com.dao.issues.R
import com.dao.issues.base.BaseActivity
import com.dao.issues.databinding.ActivityIssueDetailBinding
import com.dao.issues.model.Issue
import javax.inject.Inject

/**
 * Created in 27/03/19 10:53.
 *
 * @author Diogo Oliveira.
 */
class IssueDetailActivity : BaseActivity(), IssueDetailInteractor.View
{
    @Inject
    lateinit var presenter: IssueDetailInteractor.Presenter

    private lateinit var helper: ActivityIssueDetailBinding

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

    override fun initializeView()
    {
        setSupportActionBar(helper.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        helper.contentDetail.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            helper.appBar.isSelected = helper.contentDetail.scrollView.canScrollVertically(-1)
        }
    }

    override fun putOnForm(issue: Issue)
    {
        helper.issue = issue
    }

    override fun toast(message: Int, duration: Int)
    {
        Toast.makeText(this, message, duration).show()
    }
}