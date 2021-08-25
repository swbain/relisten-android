package com.stephenbain.relisten.com.stephenbain.relisten

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.stephenbain.relisten.R
import com.stephenbain.relisten.ui.home.HomeScreen
import com.stephenbain.relisten.ui.theme.RelistenTheme
import kotlin.time.ExperimentalTime

@ExperimentalFoundationApi
@ExperimentalTime
@ExperimentalMaterialApi
@Composable
@ExperimentalStdlibApi
fun RelistenUi() {
    RelistenTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Column {
                TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
                HomeScreen()
            }
        }
    }
}