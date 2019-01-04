package com.fantasticthing.github.router

import com.fantasticthing.github.exception.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * Created by wanbo on 2019-01-04.
 */

fun Routing.api() {
    get("/") {
        call.respondText("Hello World!")
    }
    get("/profile") {
        val username = call.request.queryParameters["username"]
        if (username.isNullOrBlank()) {
            throw ParametersMissingException(listOf("username"))
        }
        call.respondText("username is $username")
    }
}