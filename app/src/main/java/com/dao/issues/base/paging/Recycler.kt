package com.dao.issues.base.paging

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dao.issues.base.OnCollectionChangedListener

/**
 * Created in 26/03/19 21:56.
 *
 * @author Diogo Oliveira.
 */
class Recycler
{
    abstract class Adapter<T : Parcelable, V : RecyclerView.ViewHolder> constructor(
            private val context: Context,
            comparator: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, V>(comparator)
    {
        private var changedListener: OnCollectionChangedListener? = null

        override fun onBindViewHolder(holder: V, position: Int)
        {
            onBindViewHolder(holder, getItem(position))
        }

        abstract fun onBindViewHolder(holder: V, item: T?)

        protected fun <H : ViewDataBinding> inflate(parent: ViewGroup, @LayoutRes layout: Int): H
        {
            return DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false)
        }

        fun setOnCollectionChangedListener(listener: OnCollectionChangedListener)
        {
            changedListener = listener
            registerAdapterDataObserver(AdapterDataObserver())
        }

        private inner class AdapterDataObserver : RecyclerView.AdapterDataObserver()
        {
            override fun onChanged()
            {
                super.onChanged()
                collectionChanged()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int)
            {
                super.onItemRangeInserted(positionStart, itemCount)
                collectionChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int)
            {
                super.onItemRangeChanged(positionStart, itemCount)
                collectionChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?)
            {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                collectionChanged()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int)
            {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                collectionChanged()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int)
            {
                super.onItemRangeRemoved(positionStart, itemCount)
                collectionChanged()
            }

            private fun collectionChanged()
            {
                changedListener?.onCollectionChanged((itemCount == 0))
            }
        }
    }
}