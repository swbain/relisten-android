package com.stephenbain.api

import com.stephenbain.relisten.api.RelistenApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
object ApiModule {
    @Provides
    internal fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.relisten.net/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    internal fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create()
    }
}

@Module
abstract class ApiModuleBinds {
    @Binds
    internal abstract fun bindsRelistenApi(apiRetrofit: RelistenApiRetrofit): RelistenApi
}