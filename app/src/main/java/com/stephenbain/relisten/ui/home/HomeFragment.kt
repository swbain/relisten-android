package com.stephenbain.relisten.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stephenbain.relisten.R
import com.stephenbain.relisten.domain.model.Artist
import com.stephenbain.relisten.ui.core.BaseFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home.*


class HomeFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel<HomeViewModel>() }

    private val adapter by lazy { HomeAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(this, Observer { handleState(it) })

        artistsList.layoutManager = LinearLayoutManager(activity)
        artistsList.adapter = adapter
    }

    private fun handleState(state: HomeViewModel.HomeState) = when (state) {
        HomeViewModel.HomeState.Loading -> showLoading()
        is HomeViewModel.HomeState.Error -> showError()
        is HomeViewModel.HomeState.Success -> showSuccess(state.artists)
    }

    private fun showLoading() {
        loading.isVisible = true
        artistsList.isVisible = false
        errorText.isVisible = false
    }

    private fun showError() {
        errorText.isVisible = true
        artistsList.isVisible = false
        loading.isVisible = false
    }

    private fun showSuccess(artists: List<Artist>) {
        artistsList.isVisible = true
        errorText.isVisible = false
        loading.isVisible = false

        adapter.submitList(artists)
    }

    private class HomeAdapter : ListAdapter<Artist, ArtistViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
            return ArtistViewHolder(view)
        }

        override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Artist>() {
                override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                    return oldItem == newItem
                }
            }
        }

    }

    private class ArtistViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(artist: Artist) {
            name.text = artist.name
        }

    }
}