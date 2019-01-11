package com.fantasticthing.github.exception

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

/**
 * Created by wanbo on 2019-01-04.
 */

fun StatusPages.Configuration.handlerException() {
    status(HttpStatusCode.NotFound) {
        val routerErrorException = RouterErrorException()
        call.respond(routerErrorException.httpCode, routerErrorException.errorMessage())
    }
    status(HttpStatusCode.InternalServerError) {
        call.respond(HttpStatusCode.InternalServerError, HttpStatusCode.InternalServerError.description)
    }
    exception<ParametersMissingException> { case ->
        call.respond(case.httpCode, case.errorMessage())
    }
    exception<BadRequestException> { case ->
        call.respond(case.httpCode, case.errorMessage())
    }
}