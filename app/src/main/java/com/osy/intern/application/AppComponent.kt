package com.osy.intern.application

import com.osy.intern.data.api.ImgSearchAPIModule
import com.osy.intern.data.repository.ImgRepositoryModule
import com.osy.intern.ui.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    ImgSearchAPIModule::class,
    ImgRepositoryModule::class,
    ActivityBuilder::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appKey(appKey: String): Builder

        fun build(): AppComponent
    }
}