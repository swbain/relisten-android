package com.stephenbain.relisten

import com.stephenbain.relisten.ui.UiModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, UiModule::class])
interface AppComponent : AndroidInjector<RelistenApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RelistenApp>()
}