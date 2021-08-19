package com.stephenbain.relisten.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.ListState
import com.stephenbain.relisten.com.stephenbain.relisten.ui.common.toListState
import com.stephenbain.relisten.domain.GetHomeItems
import com.stephenbain.relisten.domain.HomeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalStdlibApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val getHomeItems: GetHomeItems) : ViewModel() {

    private val _state = MutableStateFlow<ListState<HomeItem>>(ListState.Loading)

    val state: StateFlow<ListState<HomeItem>>
        get() = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            delay(3000)
            getHomeItems().toListState().collect(_state::value::set)
        }
    }
}