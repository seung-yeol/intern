package com.osy.intern.ui.main

import android.view.View
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.osy.intern.R
import com.osy.intern.data.Sort
import com.osy.intern.data.repository.ImgRepository
import com.osy.intern.data.vo.ImgQueryVO
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

    private val imgQueryVO = ImgQueryVO()

    private fun doSearch(onResponse: (call: Call<ImgVO>, response: Response<ImgVO>) -> Unit) {
        imgRepository.getImageList(imgQueryVO, object : Callback<ImgVO> {
            override fun onResponse(call: Call<ImgVO>, response: Response<ImgVO>) {
                if (response.isSuccessful && response.body() != null) {
                    onResponse(call, response)
                }
            }

            override fun onFailure(call: Call<ImgVO>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private val initData = { _ : Call<ImgVO>, response: Response<ImgVO> ->
        _data.postValue(response.body())
        _listData.postValue(response.body()!!.documents)
    }

    /*
    * 여기서부터는 리스너들입니다.
    * */
    //검색버튼 클릭시
    fun searchClick(clickedView: View) {
        if (!searchText.value.isNullOrEmpty()) {
            imgQueryVO.apply {
                query = searchText.value!!
                page = 1
            }
            doSearch (initData)
        } else {
            //토스트라도 띄워줍시다.
        }
    }

    //정확도순, 최신순 체크 바뀔때
    val onCheckedChangeListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
        imgQueryVO.let {
            when (checkedId) {
                R.id.btnAccuracy -> it.sort = Sort.ACCURACY
                R.id.btnRecency -> it.sort = Sort.RECENCY
            }
        }

        //검색한 흔적이 있을때 재검색
        _listData.value?.also {
            imgQueryVO.page = 1
            if (it.size > 0){
                doSearch (initData)
            }
        }
    }

    //리스트 스크롤중 마지막 아이템을 보이게 된 경우 이미지 더 불러옴.
    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
            super.onScrollStateChanged(rv, newState)
            if ((rv.layoutManager as StaggeredGridLayoutManager)
                    .findLastVisibleItemPositions(null).toList().contains(imgQueryVO.size * imgQueryVO.page - 1)
            ) {
                imgQueryVO.page.plus(1)
                doSearch { _, response ->
                    _listData.value = mutableListOf<ImgVO.Document>().also {
                        it.addAll(_listData.value!!)
                        it.addAll(response.body()!!.documents)
                    }
                }
            }
        }
    }
}

@BindingAdapter("onScrolled")
fun RecyclerView.onScrolled(onScrollListener: RecyclerView.OnScrollListener) {
    addOnScrollListener(onScrollListener)
}

@BindingAdapter("onCheckedChange")
fun RadioGroup.onCheckedChange(onCheckedChangeListener: RadioGroup.OnCheckedChangeListener) {
    setOnCheckedChangeListener(onCheckedChangeListener)
}











