package com.fantasticthing.github.exception

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.*
import io.ktor.response.*

/**
 * Created by wanbo on 2019-01-04.
 */

fun StatusPages.Configuration.handlerException() {
    status(HttpStatusCode.NotFound) {
        val routerErrorException = RouterErrorException()
        call.respond(
            routerErrorException.httpCode,
            FreeMarkerContent(
                "error.ftl",
                ErrorMessage(
                    routerErrorException.httpCode.value.toString(),
                    routerErrorException.errorMessage().message
                )
            )
        )
    }
    status(HttpStatusCode.InternalServerError) {
        call.respond(
            HttpStatusCode.InternalServerError,
            FreeMarkerContent(
                "error-page.ftl",
                ErrorMessage(
                    HttpStatusCode.InternalServerError.value.toString(),
                    HttpStatusCode.InternalServerError.description
                )
            )
        )
    }
    exception<ErrorException> { case ->
        call.respond(case.httpCode, FreeMarkerContent("error.ftl", ErrorMessage(
            case.httpCode.value.toString(),
            case.errorMessage().message
        )))
    }
}