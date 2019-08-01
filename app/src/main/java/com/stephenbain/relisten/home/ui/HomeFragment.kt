package com.stephenbain.relisten.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.stephenbain.relisten.R
import com.stephenbain.relisten.common.observe
import com.stephenbain.relisten.common.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel<HomeViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(this, ::handleState)
        viewModel.fetchData()
    }

    private fun handleState(state: HomeViewModel.HomeState) = when (state) {
        HomeViewModel.HomeState.Loading -> showLoading()
        is HomeViewModel.HomeState.Error -> showError()
        is HomeViewModel.HomeState.Success -> showSuccess(state.items)
    }

    private fun showLoading() {
        loading.isVisible = true
        homeRecycler.isVisible = false
        errorText.isVisible = false
    }

    private fun showError() {
        errorText.isVisible = true
        homeRecycler.isVisible = false
        loading.isVisible = false
    }

    private fun showSuccess(items: List<HomeItem>) {
        homeRecycler.isVisible = true
        errorText.isVisible = false
        loading.isVisible = false

        homeRecycler.setItems(items)
    }
}
