package com.dao.issues.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.dao.issues.R
import com.dao.issues.model.Issue
import com.dao.issues.model.State
import com.dao.issues.util.CircularOutlineProvider
import com.dao.issues.util.extensions.load

/**
 * Created in 03/08/18 16:17.
 *
 * @author Diogo Oliveira.
 */
@BindingAdapter("visible")
fun visible(view: View, visible: Boolean)
{
    view.visibility = if(visible) View.VISIBLE else View.GONE
}

@BindingAdapter("createdBy", "createdAt", "shortDescription", requireAll = false)
fun created(view: TextView, issue: Issue?, date: String?, shortDescription: Boolean)
{
    issue?.let {
        if(shortDescription)
        {
            view.text = view.context.getString(R.string.issue_created, date, issue.user.login)
        }
        else
        {
            view.text = view.context.getString(R.string.issue_created_at, issue.number, date, issue.user.login)
        }
    }
}

@BindingAdapter("state")
fun state(view: TextView, state: State)
{
    when(state)
    {
        State.OPEN  -> ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(view.context, R.color.state_open))
        State.CLOSED -> ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(view.context, R.color.state_closed))
        else -> ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(view.context, android.R.color.darker_gray))
    }

    view.text = state.name
}

@BindingAdapter("avatar")
fun avatar(view: ImageView, uri: String?)
{
    view.apply {
        clipToOutline = true
        outlineProvider = CircularOutlineProvider
        load(uri)
    }
}