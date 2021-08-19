package com.stephenbain.api

import com.stephenbain.relisten.api.RelistenApi
import dagger.Binds
import dagger.Module

@Module
abstract class ApiModuleBinds {
    @Binds
    internal abstract fun bindsRelistenApi(apiRetrofit: RelistenApiRetrofit): RelistenApi
}