package com.osy.intern.data.vo

import com.osy.intern.data.Sort

//ImgRepository에서 사용할 VO
data class ImgQueryVO(
    var query: String = "",
    var sort: Sort = Sort.ACCURACY,
    var page: Int = 1,
    var size: Int = 10
)

