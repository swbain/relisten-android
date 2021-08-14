package com.stephenbain.relisten.ui.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephenbain.relisten.domain.GetHomeItems
import com.stephenbain.relisten.domain.HomeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getHomeItems: GetHomeItems) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)

    val state: StateFlow<HomeState>
        get() = _state

    init {
        viewModelScope.launch {
            delay(3000)
            getHomeItems().map(HomeState::Success).collect(_state::value::set)
        }
    }
}

@Stable
sealed class HomeState {
    @Stable
    object Loading : HomeState()
    @Stable
    data class Error(val message: String) : HomeState()
    @Stable
    data class Success(val items: List<HomeItem>) : HomeState()
}