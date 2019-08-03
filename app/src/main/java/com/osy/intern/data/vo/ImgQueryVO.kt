package com.osy.intern.data.vo

//ImgRepository에서 사용할 VO
data class ImgQueryVO(
    var query: String = "",
    var sort: Sort = Sort.ACCURACY,
    var page: Int = 1,
    var size: Int = 20
){
    enum class Sort(private val type: String) {
        ACCURACY("accuracy"), RECENCY("recency");

        override fun toString(): String {
            return type
        }
    }
}