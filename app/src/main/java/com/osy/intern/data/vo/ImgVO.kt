package com.osy.intern.data.vo

import android.os.Parcel
import android.os.Parcelable
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

    data class Document (
        @SerializedName("thumbnail_url") var thumbnailUrl: String,
        @SerializedName("image_url") var imageUrl: String,
        @SerializedName("doc_url") var docUrl: String,
        @SerializedName("display_sitename") var displaySitename: String,
        var collection: String,
        var datetime: String,
        var width: Int,
        var height: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(thumbnailUrl)
            parcel.writeString(imageUrl)
            parcel.writeString(docUrl)
            parcel.writeString(displaySitename)
            parcel.writeString(collection)
            parcel.writeString(datetime)
            parcel.writeInt(width)
            parcel.writeInt(height)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Document> {
            override fun createFromParcel(parcel: Parcel): Document {
                return Document(parcel)
            }

            override fun newArray(size: Int): Array<Document?> {
                return arrayOfNulls(size)
            }
        }
    }
}