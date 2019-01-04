package com.fantasticthing.github.exception

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*

/**
 * Created by wanbo on 2019-01-04.
 */

fun StatusPages.Configuration.handlerException() {
    exception<ParametersMissingException> { case ->
        call.respond(case.httpCode, case.errorMessage())
    }
}