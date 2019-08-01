package com.osy.intern.data.repository

import androidx.lifecycle.MutableLiveData
import com.osy.intern.data.Sort
import com.osy.intern.data.api.ImgSearchAPI
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ImgRepositoryImpl @Inject constructor(private val mImgSearchAPI: ImgSearchAPI) : ImgRepository {
    override var query: String? = null
    override var sort: Sort? = null
    override var page: Int? = null
    override var size: Int? = null

    override fun getImageList(query: String, sort: Sort, page: Int, size: Int, callback: Callback<ImgVO>) {
        mImgSearchAPI.doGetImageList(query, sort.toString(), page, size).enqueue(callback)
    }

    override fun getImageList(callback: Callback<ImgVO>) {
        getImageList(query!!, sort!!, page!!, size!!, callback)
    }
}