package com.fantasticthing.github.exception

import io.ktor.http.*

/**
 * Created by wanbo on 2019-01-04.
 */

// 参数缺失
class ParametersMissingException(private val keys: List<String>): ErrorException() {

    override val httpCode: HttpStatusCode
        get() = HttpStatusCode.BadRequest

    override val errorCode: String
        get() = "ParametersMissing"

    override fun errorMessage(): ErrorMessage = ErrorMessage(errorCode, "Parameters $keys Missing")

}