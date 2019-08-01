package com.osy.intern.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.osy.intern.data.Sort
import com.osy.intern.data.api.ImgSearchAPI
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ImgRepositoryImpl @Inject constructor(private val mImgSearchAPI: ImgSearchAPI) : ImgRepository {
    override fun doGetImageList(query: String, sort: Sort, page: Int, size: Int): LiveData<ImgVO> {
        val data = MutableLiveData<ImgVO>()

        mImgSearchAPI.doGetImageList(query, sort.toString(), page, size).enqueue(object : Callback<ImgVO> {
            override fun onResponse(call: Call<ImgVO>, response: Response<ImgVO>) {
                if (response.isSuccessful && response.body() != null) {
                    data.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ImgVO>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return data
    }
}