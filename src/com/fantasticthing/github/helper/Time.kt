package com.fantasticthing.github.helper

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