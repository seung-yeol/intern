package com.osy.intern.data

enum class Sort(private val type: String) {
    ACCURACY("accuracy"), RECENCY("recency");

    override fun toString(): String {
        return type
    }
}