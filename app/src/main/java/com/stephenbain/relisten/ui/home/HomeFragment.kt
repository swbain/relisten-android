package com.stephenbain.relisten.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.stephenbain.relisten.R
import com.stephenbain.relisten.ui.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {

    private val viewModel by lazy { getViewModel<HomeViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getText.observe(this, Observer { text.text = it  })
    }

}