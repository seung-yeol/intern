package com.osy.intern.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.osy.intern.data.Sort
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call
import retrofit2.Callback

interface ImgRepository {
    var query: String?
    var sort: Sort?
    var page: Int?
    var size: Int?

    fun getImageList(callback: Callback<ImgVO>)
    fun getImageList(query: String, sort: Sort, page: Int, size: Int, callback: Callback<ImgVO>)
}