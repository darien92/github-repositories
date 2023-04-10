package com.darien.repositories_data.model.domain

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

data class RepositoryDomainModel(
    val id: Int,
    val name: String,
    val avatar: String,
    val htmlUrl: String,
    val description: String,
    val language: String,
    val starsCount: Int,
    val lastUpdated: String
) {
        fun parseStars(): String {
            return if (starsCount < 1000) {
                "$starsCount"
            } else if (starsCount < 1000000) {
                val count = starsCount / 1000
                "$count k"
            } else {
                val count = starsCount / 1000000
                "$count M"
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun parseDate(): String {
            val subStr = lastUpdated.substring(startIndex = 0, endIndex = 10)
            val date = SimpleDateFormat("yyyy-MM-dd").parse(subStr)
            val format = SimpleDateFormat("MMM, dd - yyyy")
            return format.format(date!!)
        }
}
