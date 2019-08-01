package com.stephenbain.relisten

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun provideContext(app: RelistenApp): Context

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun providesBackgroundScheduler(): Scheduler {
            return Schedulers.from(Executors.newFixedThreadPool(10))
        }
    }
}
