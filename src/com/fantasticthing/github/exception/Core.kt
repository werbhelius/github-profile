package com.fantasticthing.github.exception

import io.ktor.http.*

/**
 * Created by wanbo on 2019-01-04.
 */

abstract class ErrorException : RuntimeException() {
    abstract val httpCode: HttpStatusCode
    abstract val errorCode: String
    abstract fun errorMessage(): ErrorMessage
}

data class ErrorMessage(val code: String, val message: String)