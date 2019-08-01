package com.osy.intern.data.repository

import androidx.lifecycle.LiveData
import com.osy.intern.data.Sort
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call

interface ImgRepository {
    fun doGetImageList(query: String, sort: Sort, page: Int, size: Int) : LiveData<ImgVO>
}