package com.osy.intern.data.repository

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ImgRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindImgRepository(imgRepository: ImgRepositoryImpl): ImgRepository
}