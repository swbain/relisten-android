package com.stephenbain.relisten.home

import androidx.lifecycle.ViewModel
import com.stephenbain.relisten.common.api.ApiModule
import com.stephenbain.relisten.common.db.DbModule
import com.stephenbain.relisten.common.ui.ViewModelKey
import com.stephenbain.relisten.home.ui.HomeFragment
import com.stephenbain.relisten.home.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [HomeModule.BindViewModel::class])
abstract class HomeModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @Module(includes = [ApiModule::class, DbModule::class])
    abstract class BindViewModel {
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
    }
}