package com.osy.intern.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.osy.intern.data.Sort
import com.osy.intern.data.repository.ImgRepository
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val imgRepository: ImgRepository) : ViewModel() {
    val searchText = MutableLiveData<String>()

    private val _data = MutableLiveData<ImgVO>()
    val data: LiveData<ImgVO>
        get() = _data

    private val _sort = MutableLiveData<Sort>()
    val sort: LiveData<Sort>
        get() = _sort

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page

    private val _size = MutableLiveData<Int>()
    val size: LiveData<Int>
        get() = _size

    init {
        _sort.value = Sort.Accuracy
        _page.value = 1
        _size.value = 10
    }

    fun searchClick(clickedView: View) {
        if (!searchText.value.isNullOrEmpty()) {
            imgRepository.apply {
                query = searchText.value
                sort = _sort.value
                page = _page.value
                size = _size.value
            }.getImageList(object : Callback<ImgVO> {
                override fun onResponse(call: Call<ImgVO>, response: Response<ImgVO>) {
                    if (response.isSuccessful && response.body() != null) {
                        _data.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ImgVO>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}