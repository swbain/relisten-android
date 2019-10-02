package com.stephenbain.relisten.common.api

import com.squareup.moshi.Moshi
import com.stephenbain.relisten.common.api.adapters.ArtistJsonAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object ApiModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesRelistenApi(): RelistenApi {
        val moshi = Moshi.Builder().add(ArtistJsonAdapter()).build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://api.relisten.net/api/v2/")
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(MoshiConverterFactory.create(moshi))
        }.build()

        return retrofit.create(RelistenApi::class.java)
    }
}
