package com.stephenbain.relisten.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.ContentState
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.toContentState
import com.stephenbain.relisten.domain.GetHomeItems
import com.stephenbain.relisten.domain.HomeItem
import com.stephenbain.relisten.domain.HomeSeparator
import com.stephenbain.relisten.domain.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalStdlibApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val getHomeItems: GetHomeItems) : ViewModel() {

    private val _state = MutableStateFlow<ContentState<HomeState>>(ContentState.Loading)

    val state: StateFlow<ContentState<HomeState>>
        get() = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            getHomeItems().toContentState().collect(_state::value::set)
        }
    }
}