package com.stephenbain.relisten.data

import com.stephenbain.relisten.data.api.ApiModule
import dagger.Module

@Module(includes = [ApiModule::class])
class DataModule