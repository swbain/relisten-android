package com.stephenbain.relisten.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected inline fun <reified T : ViewModel> getViewModel(): T {
        return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
    }

}