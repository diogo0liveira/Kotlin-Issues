package com.dao.issues.base

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created in 26/03/19 21:56.
 *
 * @author Diogo Oliveira.
 */
class Recycler
{
    abstract class Adapter<T : Parcelable, V : RecyclerView.ViewHolder> constructor(
            private val context: Context,
            private var list: MutableList<T>) : RecyclerView.Adapter<V>()
    {
        private var changedListener: OnCollectionChangedListener? = null

        private fun getItem(position: Int): T = list[position]

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: V, position: Int)
        {
            onBindViewHolder(holder, getItem(position))
        }

        abstract fun onBindViewHolder(holder: V, item: T)

        @Suppress("SameParameterValue")
        protected fun <H : ViewDataBinding> inflate(parent: ViewGroup, @LayoutRes layout: Int): H
        {
            return DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false)
        }

        fun setDataList(list: MutableList<T>)
        {
            this.list = list
            notifyDataSetChanged()
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