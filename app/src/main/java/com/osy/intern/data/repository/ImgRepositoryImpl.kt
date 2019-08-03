package com.osy.intern.data.repository

import androidx.lifecycle.MutableLiveData
import com.osy.intern.data.Sort
import com.osy.intern.data.api.ImgSearchAPI
import com.osy.intern.data.vo.ImgQueryVO
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ImgRepositoryImpl @Inject constructor(private val mImgSearchAPI: ImgSearchAPI) : ImgRepository {
    override fun getImageList(imgQueryVO: ImgQueryVO, callback: Callback<ImgVO>) {
        mImgSearchAPI.doGetImageList(imgQueryVO.query, imgQueryVO.sort.toString(), imgQueryVO.page, imgQueryVO.size).enqueue(callback)
    }
}