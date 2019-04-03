package com.stephenbain.relisten.di

import android.content.Context
import com.stephenbain.relisten.RelistenApp
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {
    @Singleton
    @Binds
    fun provideContext(app: RelistenApp): Context
}