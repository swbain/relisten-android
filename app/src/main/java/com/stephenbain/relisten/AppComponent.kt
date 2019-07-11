package com.stephenbain.relisten

import com.stephenbain.relisten.common.ui.UiModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, UiModule::class])
interface AppComponent : AndroidInjector<RelistenApp> {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}