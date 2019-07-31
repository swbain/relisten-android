package com.stephenbain.relisten.common.ui.widget

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseListAdapter<T, VH : BaseViewHolder<T>>(
    sameItems: (oldItem: T, newItem: T) -> Boolean,
    sameContents: (oldItem: T, newItem: T) -> Boolean
) : ListAdapter<T, VH>(object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = sameItems(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T) = sameContents(oldItem, newItem)
})

abstract class BaseViewHolder<T>(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    abstract fun bind(item: T)
}
