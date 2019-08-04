package com.osy.intern

import com.osy.intern.data.api.ImgSearchAPI
import com.osy.intern.data.repository.ImgRepository
import com.osy.intern.data.repository.ImgRepositoryImpl
import org.junit.Test

import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    lateinit var imgRepository : ImgRepository

    @Before
    fun setUp(){
        imgRepository = ImgRepositoryImpl(ImgSearchAPI.Factory.create("7dcccc098808141496798bab2ee7d3e1"))
    }

    @Test
    fun apiTest() {
    }
}
