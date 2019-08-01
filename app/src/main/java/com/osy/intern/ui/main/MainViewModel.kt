package com.osy.intern.ui.main

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    private val _listData = MutableLiveData<MutableList<ImgVO.Document>>()
    val listData: LiveData<MutableList<ImgVO.Document>>
        get() = _listData

    val searchText = MutableLiveData<String>()
    val sort: Sort = Sort.Accuracy

    private var page = 1
    private val size = 10

    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            //스크롤중 마지막 아이템을 보이게 된 경우 이미지 더 불러옴.
            if ((recyclerView.layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null).toList().contains(size * page - 1)) {
                page++
                imgRepository
                    .apply { page = this@MainViewModel.page }
                    .getImageList(object : Callback<ImgVO> {
                        override fun onResponse(call: Call<ImgVO>, response: Response<ImgVO>) {
                            if (response.isSuccessful && response.body() != null) {
                                _listData.value = mutableListOf<ImgVO.Document>().also {
                                    it.addAll(_listData.value!!)
                                    it.addAll(response.body()!!.documents)
                                }
                            }
                        }

                        override fun onFailure(call: Call<ImgVO>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }
        }
    }

    fun searchClick(clickedView: View) {
        page = 1
        if (!searchText.value.isNullOrEmpty()) {
            imgRepository.apply {
                query = searchText.value
                sort = this@MainViewModel.sort
                size = this@MainViewModel.size
                page = this@MainViewModel.page
            }.getImageList(object : Callback<ImgVO> {
                override fun onResponse(call: Call<ImgVO>, response: Response<ImgVO>) {
                    if (response.isSuccessful && response.body() != null) {
                        _data.postValue(response.body())
                        _listData.postValue(response.body()!!.documents)
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

@BindingAdapter("onScrolled")
fun RecyclerView.onScrolled(onScrollListener: RecyclerView.OnScrollListener) {
    addOnScrollListener(onScrollListener)
}











