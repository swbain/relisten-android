package com.stephenbain.relisten.artist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stephenbain.relisten.common.Artist
import com.stephenbain.relisten.common.ui.BaseViewModel
import javax.inject.Inject


class ArtistViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableLiveData<ArtistState>()

    val state: LiveData<ArtistState>
        get() = _state

    fun loadArtist(artist: Artist) {
        _state.value = ArtistState.Success(artist)
    }

    sealed class ArtistState {
        data class Success(val artist: Artist) : ArtistState()
    }

}