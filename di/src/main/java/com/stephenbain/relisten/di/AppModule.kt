package com.stephenbain.relisten.di

import com.stephenbain.api.ApiModule
import com.stephenbain.api.ApiModuleBinds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [
    ApiModule::class,
    ApiModuleBinds::class,
])
@InstallIn(SingletonComponent::class)
interface AppModule