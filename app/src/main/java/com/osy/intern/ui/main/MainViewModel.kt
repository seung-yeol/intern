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
    private val _data = MutableLiveData<ImgVO>()
    val data: LiveData<ImgVO>
        get() = _data

    val searchText = MutableLiveData<String>()
    val page = MutableLiveData<Int>()
    val sort: Sort = Sort.Accuracy

    private val size = 20

    init {
        page.value = 1
    }

    fun searchClick(clickedView: View) {
        if (!searchText.value.isNullOrEmpty()) {
            imgRepository.apply {
                query = searchText.value
                sort = this@MainViewModel.sort
                size = this@MainViewModel.size
                page = this@MainViewModel.page.value
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
        } else {
            //토스트라도 띄워줍시다.
        }
    }
}