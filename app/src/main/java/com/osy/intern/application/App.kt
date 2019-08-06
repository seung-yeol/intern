package com.osy.intern.application

import com.osy.intern.R
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication(){
    companion object{
        val SEARCH_SIZE = 20
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().appKey(resources.getString(R.string.kakao_restapi_key)).build()
    }
}