package com.osy.intern.data.repository

import com.osy.intern.data.api.ImgSearchAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ImgRepositoryModule {
    @Binds
    abstract fun bindImgRepository(imgRepository: ImgRepositoryImpl): ImgRepository
}