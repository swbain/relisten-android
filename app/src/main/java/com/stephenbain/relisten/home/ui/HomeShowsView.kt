package com.stephenbain.relisten.home.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stephenbain.relisten.R
import com.stephenbain.relisten.common.Show
import com.stephenbain.relisten.common.ui.widget.BaseListAdapter
import com.stephenbain.relisten.common.ui.widget.BaseViewHolder
import kotlinx.android.synthetic.main.show_item.*

class HomeShowsView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    private val showsAdapter = ShowsAdapter()

    override fun onFinishInflate() {
        super.onFinishInflate()
        adapter = showsAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setShows(shows: List<Show>) {
        showsAdapter.submitList(shows)
    }

    private class ShowsAdapter : BaseListAdapter<Show, ShowViewHolder>(::sameItems, ::sameContents) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
            return ShowViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false))
        }

        override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    private class ShowViewHolder(override val containerView: View) : BaseViewHolder<Show>(containerView) {
        override fun bind(item: Show) {
            showDate.text = item.displayDate
            showArtist.text = item.artist.name
        }
    }

    companion object {
        private fun sameItems(oldItem: Show, newItem: Show) = false
        private fun sameContents(oldItem: Show, newItem: Show) = false
    }
}
