package com.dao.issues.features.issues

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.issues.R
import com.dao.issues.base.BaseActivity
import com.dao.issues.base.Recycler
import com.dao.issues.databinding.ActivityIssuesBinding
import com.dao.issues.databinding.ViewEmptyIssuesBinding
import com.dao.issues.model.Issue
import javax.inject.Inject

/**
 * Created in 26/03/19 21:01.
 *
 * @author Diogo Oliveira.
 */
class IssuesActivity : BaseActivity(), IssuesInteractor.View, Recycler.Adapter.OnCollectionChangedListener, IssuesAdapter.IssueViewOnClickListener
{
    @Inject
    lateinit var presenter: IssuesInteractor.Presenter

    private lateinit var helperEmpty: ViewEmptyIssuesBinding
    private lateinit var helper: ActivityIssuesBinding

    private val adapter: IssuesAdapter by lazy {
        IssuesAdapter(this, mutableListOf(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_issues)
        presenter.initialize(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_issues, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId)
        {
            R.id.menu_information ->
            {
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
        supportActionBar?.apply {
            setLogo(R.drawable.ic_launcher_foreground)
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
        }

        if(helper.messageEmpty.isInflated)
        {
            helperEmpty.visible = true
        }
        else
        {
            helper.messageEmpty.viewStub!!.visibility = View.VISIBLE
            helperEmpty = DataBindingUtil.findBinding(helper.messageEmpty.root)!!
            helperEmpty.visible = true
        }

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        helper.issuesList.addItemDecoration(divider)
        helper.issuesList.setHasFixedSize(true)

        adapter.setOnCollectionChangedListener(this)
        helper.issuesList.adapter = adapter

        presenter.loadIssuesList()
    }

    override fun startIssuesDetailActivity()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onIssueViewOnClick(issue: Issue)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadingIssuesList(list: List<Issue>)
    {
        adapter.setDataList(list.toMutableList())
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
