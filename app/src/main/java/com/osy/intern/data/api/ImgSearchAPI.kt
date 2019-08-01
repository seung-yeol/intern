package com.osy.intern.data.api

import com.osy.intern.data.vo.ImgVO
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ImgSearchAPI {

    @GET("image")
    fun doGetImageList(@Query("query") query: String,
                       @Query("sort") sort: String,
                       @Query("page") page: Int,
                       @Query("size") size: Int): Call<ImgVO>

    object Factory {
        fun create(appKey: String): ImgSearchAPI {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .addHeader("Authorization", "KakaoAK $appKey")
                            .build()
                    )
                }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/v2/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ImgSearchAPI::class.java)
        }
    }

}