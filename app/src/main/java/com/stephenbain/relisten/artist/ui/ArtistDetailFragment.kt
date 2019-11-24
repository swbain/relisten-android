package com.stephenbain.relisten.artist.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.stephenbain.relisten.R
import com.stephenbain.relisten.common.observe
import com.stephenbain.relisten.common.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_artist.*

class ArtistDetailFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_artist

    private val viewModel by getViewModel<ArtistViewModel>()
    private val args by navArgs<ArtistDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadArtist(args.artist)
        viewModel.state.observe(this) { state ->
            when (state) {
                is ArtistViewModel.ArtistState.Success -> text.text = state.artist.name
            }
        }
    }
}
