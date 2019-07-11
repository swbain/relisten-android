package com.stephenbain.relisten.home.ui

import androidx.lifecycle.ViewModel
import com.stephenbain.relisten.common.api.ApiModule
import com.stephenbain.relisten.common.ui.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [HomeModule.BindViewModel::class, ApiModule::class])
abstract class HomeModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @Module
    abstract class BindViewModel {
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
    }
}