package com.osy.intern.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.osy.intern.data.Sort
import com.osy.intern.data.vo.ImgQueryVO
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call
import retrofit2.Callback

interface ImgRepository {
    fun getImageList(imgQueryVO: ImgQueryVO, callback: Callback<ImgVO>)
}