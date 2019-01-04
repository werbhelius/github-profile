package com.fantasticthing.github.exception

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

/**
 * Created by wanbo on 2019-01-04.
 */

fun StatusPages.Configuration.handlerException() {
    exception<AuthenticationException> {
        call.respond(HttpStatusCode.Unauthorized, "please fill in correct username")
    }
    exception<AuthorizationException> {
        call.respond(HttpStatusCode.Forbidden)
    }
}