package com.fantasticthing.github.helper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import java.util.*
import java.text.SimpleDateFormat

/**
 * Created by wanbo on 2019-01-11.
 */

fun getFromAndToTime(): Pair<String, String> {

    val to = Calendar.getInstance()
    val from = Calendar.getInstance().apply {
        add(Calendar.YEAR, -1)
    }

    val dataFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val isoTo = dataFormat.format(Date(to.timeInMillis))
    val isoFrom = dataFormat.format(Date(from.timeInMillis))

    return Pair(isoFrom, isoTo)
}

fun Any.toJson(): String {
    return jacksonObjectMapper().writeValueAsString(this)
}

fun Any.toGraphQLBody(): TextContent {
    return TextContent(
        this.toJson(), contentType = ContentType.Application.Json
    )
}

inline fun <reified T> String.toAny(): T {
    return jacksonObjectMapper().readValue(this, T::class.java)
}

fun <T> List<T>.subListSafe(fromIndex: Int, toIndex: Int): List<T> {
    var from = fromIndex
    var to = toIndex
    if (fromIndex < 0) {
        from = 0
    }
    if (toIndex > size) {
        to = size
    }
    return  this.subList(from, to)
}