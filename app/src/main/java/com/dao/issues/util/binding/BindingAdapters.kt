package com.dao.issues.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.PrecomputedTextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.dao.issues.R
import com.dao.issues.model.Issue
import com.dao.issues.model.State
import com.dao.issues.util.CircularOutlineProvider
import com.dao.issues.util.extensions.load
import java.text.SimpleDateFormat
import java.util.*

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

@BindingAdapter("created")
fun created(view: TextView, date: String)
{
    val parse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    parse.timeZone = TimeZone.getDefault()

    val dateFormatted = SimpleDateFormat("EEE, MMM d, yy", Locale.ENGLISH).format(parse.parse(date))
    view.text = dateFormatted
}

@BindingAdapter("createdAt", "shortDescription", requireAll = false)
fun createdAt(view: TextView, issue: Issue, shortDescription: Boolean)
{
    val parse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    parse.timeZone = TimeZone.getDefault()

    val date = parse.parse(issue.created)
    val dateFormatted = SimpleDateFormat("EEE, MMM d, yy", Locale.ENGLISH).format(date)

    if(shortDescription)
    {
        view.text = view.context.getString(R.string.issue_created, dateFormatted, issue.user.login)
    }
    else
    {
        view.text = view.context.getString(R.string.issue_created_at, issue.number, dateFormatted, issue.user.login)
    }
}

@BindingAdapter("state")
fun state(view: TextView, state: State)
{
    when(state)
    {
        State.OPEN  -> ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(view.context, R.color.state_open))
        State.CLOSE -> ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(view.context, R.color.state_close))
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

@BindingAdapter("asyncText", "android:textSize", requireAll = false)
fun asyncText(view: TextView, text: CharSequence, textSize: Int?)
{
    if(textSize != null)
    {
        view.textSize = textSize.toFloat()
    }

    val params = TextViewCompat.getTextMetricsParams(view)
    (view as AppCompatTextView).setTextFuture(PrecomputedTextCompat.getTextFuture(text, params, null))
}