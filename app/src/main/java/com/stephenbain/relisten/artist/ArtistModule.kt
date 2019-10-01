package com.stephenbain.relisten.artist

import com.stephenbain.relisten.artist.ui.ArtistDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ArtistModule {
    @ContributesAndroidInjector
    abstract fun contributeArtistFragment(): ArtistDetailFragment
}
