package com.osy.intern.data.vo

import com.google.gson.annotations.SerializedName


data class ImgVO(
    var meta: Meta,
    var documents: MutableList<Document>
) {
    data class Meta(
        @SerializedName("total_count") var totalCount: Int,
        @SerializedName("pageable_count") var pageableCount: Int,
        @SerializedName("is_end") var isEnd: Boolean
    )

    data class Document(
        @SerializedName("thumbnail_url") var thumbnailUrl: String,
        @SerializedName("image_url") var imageUrl: String,
        @SerializedName("doc_url") var docUrl: String,
        @SerializedName("display_sitename") var displaySitename: String,
        var collection: String,
        var datetime: String,
        var width: Int,
        var height: Int
    )
}