package com.stephenbain.relisten

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.stephenbain.relisten.com.stephenbain.relisten.RelistenUi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalStdlibApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RelistenUi()
        }
    }
}