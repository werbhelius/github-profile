package com.fantasticthing.github.exception

import io.ktor.http.*

/**
 * Created by wanbo on 2019-01-04.
 */

class ParametersMissingException(private val keys: List<String>): ErrorException() {

    override val httpCode: HttpStatusCode
        get() = HttpStatusCode.BadRequest

    override val errorCode: String
        get() = "ParametersMissing"

    override fun errorMessage(): ErrorMessage = ErrorMessage(errorCode, "Parameters $keys Missing")

}

class RouterErrorException: ErrorException() {

    override val httpCode: HttpStatusCode
        get() = HttpStatusCode.NotFound

    override val errorCode: String
        get() = "RouterError"

    override fun errorMessage(): ErrorMessage = ErrorMessage(errorCode, "Router Not Found")

}

class BadRequestException(private val error: Any): ErrorException() {

    override val httpCode: HttpStatusCode
        get() = HttpStatusCode.BadRequest

    override val errorCode: String
        get() = "BadRequest"

    override fun errorMessage(): ErrorMessage = ErrorMessage(errorCode, error)

}

class ParametersNotFoundException(private val error: Any): ErrorException() {

    override val httpCode: HttpStatusCode
        get() = HttpStatusCode.NotFound

    override val errorCode: String
        get() = "ParametersNotFound"

    override fun errorMessage(): ErrorMessage = ErrorMessage(errorCode, error)

}

class InternalServerErrorException : ErrorException() {
    override val httpCode: HttpStatusCode
        get() = HttpStatusCode.InternalServerError

    override val errorCode: String
        get() = "InternalServerError"

    override fun errorMessage(): ErrorMessage = ErrorMessage(errorCode, HttpStatusCode.InternalServerError.description)

}