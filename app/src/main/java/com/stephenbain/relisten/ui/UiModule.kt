package com.stephenbain.relisten.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stephenbain.relisten.api.ApiModule
import com.stephenbain.relisten.ui.home.HomeModule
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Provider
import javax.inject.Singleton


@Module(includes = [AndroidSupportInjectionModule::class, HomeModule::class, ApiModule::class])
class UiModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val creator = creators[modelClass] ?: creators.entries.firstOrNull {
                modelClass.isAssignableFrom(it.key)
            }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
            try {
                @Suppress("UNCHECKED_CAST")
                return creator.get() as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

}