package com.osy.intern.data.vo

import com.osy.intern.application.App

//ImgRepository에서 사용할 VO
data class ImgQueryVO(
    var query: String = "",
    var sort: Sort = Sort.ACCURACY,
    var page: Int = 1,
    var size: Int = App.SEARCH_SIZE
){
    enum class Sort(private val type: String) {
        ACCURACY("accuracy"), RECENCY("recency");

        override fun toString(): String {
            return type
        }
    }
}