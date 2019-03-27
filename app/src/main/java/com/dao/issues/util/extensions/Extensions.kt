package com.dao.issues.util.extensions

import android.widget.ImageView
import com.dao.issues.R
import com.dao.issues.util.GlideApp

/**
 * Created in 27/03/19 16:25.
 *
 * @author Diogo Oliveira.
 */
fun ImageView.load(uri: String?)
{
    GlideApp.with(context)
            .load(uri)
            .placeholder(R.drawable.ic_account_circle_24dp)
            .error(R.drawable.ic_account_circle_24dp)
            .into(this)
}