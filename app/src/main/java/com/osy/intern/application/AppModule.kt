package com.osy.intern.application

import androidx.lifecycle.ViewModelProvider
import com.osy.intern.ui.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}