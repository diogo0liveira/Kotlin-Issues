package com.dao.issues.features.issues

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.issues.DEVELOPER_GITHUB
import com.dao.issues.Extras
import com.dao.issues.R
import com.dao.issues.base.BaseActivity
import com.dao.issues.base.OnCollectionChangedListener
import com.dao.issues.databinding.ActivityIssuesBinding
import com.dao.issues.databinding.ViewEmptyIssuesBinding
import com.dao.issues.features.detail.IssueDetailActivity
import com.dao.issues.model.Issue
import com.dao.issues.network.State
import javax.inject.Inject

/**
 * Created in 26/03/19 21:01.
 *
 * @author Diogo Oliveira.
 */
class IssuesActivity : BaseActivity(), IssuesInteractor.View, OnCollectionChangedListener, IssuesAdapter.IssueViewOnClickListener
{
    @Inject
    lateinit var presenter: IssuesInteractor.Presenter

    private lateinit var helperEmpty: ViewEmptyIssuesBinding
    private lateinit var helper: ActivityIssuesBinding

    private val adapter: IssuesAdapter by lazy { IssuesAdapter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_issues)

        presenter.initialize(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.option_issues, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId)
        {
            R.id.menu_information ->
            {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DEVELOPER_GITHUB))
                startActivity(intent)
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
        adapter.dispose()
        presenter.terminate()
    }

    override fun initializeView()
    {
        supportActionBar?.apply {
            setLogo(R.drawable.ic_launcher_foreground)
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
        }

        helper.messageEmpty.viewStub?.inflate()?.let { view ->
            DataBindingUtil.getBinding<ViewEmptyIssuesBinding>(view)?.let { binding ->
                helperEmpty = binding
                helperEmpty.visible = true
            }
        }

        with(helper.issuesList) {
            val divider = DividerItemDecoration(this@IssuesActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
            setHasFixedSize(true)
        }

        adapter.setOnCollectionChangedListener(this)
        helper.issuesList.adapter = adapter

        presenter.issuesObserver().observe(this, Observer { adapter.submitList(it) })

        presenter.loadIssues().observe(this, Observer {
            when(it.status)
            {
                State.RUNNING -> showLoading()
                State.SUCCESS -> hideLoading()
                State.FAILED ->
                {
                    hideLoading()
                    executeRequireNetwork { it.retry?.invoke() }
                }
            }
        })

        helper.swipeRefresh.setOnRefreshListener {
            executeRequireNetwork {
                presenter.refreshIssues()
                helperEmpty.visible
            }
        }
    }

    override fun showLoading()
    {
        helper.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading()
    {
        helper.swipeRefresh.isRefreshing = false
    }

    override fun onIssueViewOnClick(issue: Issue)
    {
        startIssuesDetailActivity(issue)
    }

    override fun startIssuesDetailActivity(issue: Issue)
    {
        val intent = Intent(this, IssueDetailActivity::class.java)
        intent.putExtra(Extras.ISSUE, issue)
        startActivity(intent)
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
//        if(adapter.itemCount > 0)
//        {
//            helperEmpty.visible = false
//        }
//        else
//        {
//            helperEmpty.visible = (adapter.itemCount > 0) || isEmpty
//        }

        helperEmpty.visible = ((adapter.itemCount > 0) || isEmpty.not())
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
            helper.swipeRefresh.isRefreshing = false
            notifyDisconnected(helper.anchor) { executeRequireNetwork(block) }
        }
    }

    override fun toast(message: Int, duration: Int)
    {
        Toast.makeText(this, message, duration).show()
    }
}
