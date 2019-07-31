package com.osy.intern.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().build()
    }
}