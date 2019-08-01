package com.osy.intern.data.api

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImgSearchAPIModule {
    @Provides
    @Singleton
    fun bindImgSearchAPI(appKey: String): ImgSearchAPI {
        return ImgSearchAPI.Factory.create(appKey)
    }
}