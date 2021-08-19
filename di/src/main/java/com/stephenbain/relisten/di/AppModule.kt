package com.stephenbain.relisten.di

import com.stephenbain.api.ApiModuleBinds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ApiModuleBinds::class])
@InstallIn(SingletonComponent::class)
interface AppModule