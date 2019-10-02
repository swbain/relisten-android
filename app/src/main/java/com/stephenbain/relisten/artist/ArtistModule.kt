package com.stephenbain.relisten.artist

import androidx.lifecycle.ViewModel
import com.stephenbain.relisten.artist.ui.ArtistDetailFragment
import com.stephenbain.relisten.artist.ui.ArtistViewModel
import com.stephenbain.relisten.common.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [ArtistModule.BindViewModel::class])
abstract class ArtistModule {
    @ContributesAndroidInjector
    abstract fun contributeArtistFragment(): ArtistDetailFragment

    @Module
    abstract class BindViewModel {
        @Binds
        @IntoMap
        @ViewModelKey(ArtistViewModel::class)
        abstract fun bindArtistViewModel(viewModel: ArtistViewModel): ViewModel
    }
}
