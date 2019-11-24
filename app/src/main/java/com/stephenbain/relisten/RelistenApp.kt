package com.stephenbain.relisten

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class RelistenApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
