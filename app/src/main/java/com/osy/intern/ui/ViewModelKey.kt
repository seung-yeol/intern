package com.osy.intern.ui

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

//google iosched에서 보고 옴!
@Target(
        AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)