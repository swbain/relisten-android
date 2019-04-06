package com.stephenbain.relisten.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class HomeViewModel @Inject constructor() : ViewModel() {

    val getText: LiveData<String>
        get() {
            val data = MutableLiveData<String>()
            data.value = "this is the home page"
            return data
        }

}