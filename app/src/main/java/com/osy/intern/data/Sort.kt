package com.osy.intern.data

enum class Sort(private val type: String) {
    Accuracy("accuracy"), Recency("recency");

    override fun toString(): String {
        return type
    }
}