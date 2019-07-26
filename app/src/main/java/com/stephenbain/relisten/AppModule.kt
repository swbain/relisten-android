package com.stephenbain.relisten

import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppModule {
    @Singleton
    @Binds
    fun provideContext(app: RelistenApp): Context
}