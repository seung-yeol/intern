package com.osy.intern.ui.main

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.osy.intern.R
import com.osy.intern.data.repository.ImgRepository
import com.osy.intern.data.vo.ImgQueryVO
import com.osy.intern.data.vo.ImgVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val imgRepository: ImgRepository) : ViewModel() {
    private val _imgData = MutableLiveData<MutableList<ImgVO.Document>>()
    val imgData: LiveData<MutableList<ImgVO.Document>>
        get() = _imgData

    private val _isInit = MutableLiveData<Boolean>()
    val isInit: LiveData<Boolean>
        get() = _isInit

    private val _doKeyboardHide = MutableLiveData<Int>()
    val doKeyboardHide: LiveData<Int>
        get() = _doKeyboardHide

    val searchText = MutableLiveData<String>()

    private var meta: ImgVO.Meta? = null
    private var isWork: Boolean = false //true인 경우 검색실행x
    private val imgQueryVO = ImgQueryVO()

    private fun doSearch(onResponse: (call: Call<ImgVO>, response: Response<ImgVO>) -> Unit) {
        isWork = true
        imgRepository.getImageList(imgQueryVO, object : Callback<ImgVO> {
            override fun onResponse(call: Call<ImgVO>, response: Response<ImgVO>) {
                if (response.isSuccessful && response.body() != null) {
                    onResponse(call, response)
                    isWork = false
                }
            }

            override fun onFailure(call: Call<ImgVO>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private val initData = { _: Call<ImgVO>, response: Response<ImgVO> ->
        meta = response.body()!!.meta
        _imgData.postValue(response.body()!!.documents)
        _isInit.postValue(true)

    }

    /*
    * 여기서부터는 리스너들입니다.
    * */
    //검색버튼 클릭시
    fun searchClick(clickedView: View?) {
        if (!searchText.value.isNullOrEmpty()) {
            imgQueryVO.apply {
                query = searchText.value!!
                page = 1
            }
            doSearch(initData)
        } else {
            _isInit.value = false
        }
    }

    //정확도순, 최신순 체크 바뀔때
    val onCheckedChangeListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
        imgQueryVO.let {
            when (checkedId) {
                R.id.btnAccuracy -> it.sort = ImgQueryVO.Sort.ACCURACY
                R.id.btnRecency -> it.sort = ImgQueryVO.Sort.RECENCY
            }
        }

        //검색한 흔적이 있을때 재검색
        _imgData.value?.also {
            if (it.size > 0) {
                imgQueryVO.page = 1
                doSearch(initData)
            }
        }
    }

    //api 실행 중 아니면서 && 모든 이미지 불러오지 않았을때 && 리스트 스크롤중 보여줄게 5개이하로 남았을 때
    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
            super.onScrollStateChanged(rv, newState)
            if (!isWork && (if (meta == null) false else !meta!!.isEnd) && (rv.layoutManager as StaggeredGridLayoutManager)
                    .findLastVisibleItemPositions(null).toList()[1] > imgQueryVO.size * imgQueryVO.page - 6
            ) {
                imgQueryVO.page++
                doSearch { _, response ->
                    meta = response.body()!!.meta
                    _imgData.postValue(mutableListOf<ImgVO.Document>().also {
                        it.addAll(_imgData.value!!)
                        it.addAll(response.body()!!.documents)
                    })
                }
            }
        }
    }

    val onEditorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        //키보드의 확인버튼을 누르면 search버튼을 클릭한 것과 같은 효과.
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            _doKeyboardHide.value = 1
            searchClick(null)
        }
        false
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

@BindingAdapter("onEditorAction")
fun EditText.onEditorAction(onEditorActionListener: TextView.OnEditorActionListener) {
    setOnEditorActionListener(onEditorActionListener)
}