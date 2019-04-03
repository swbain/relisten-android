package com.stephenbain.relisten.di

import com.stephenbain.relisten.RelistenApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<RelistenApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RelistenApp>()
}