package com.fantasticthing.github.router

import com.fantasticthing.github.exception.*
import com.fantasticthing.github.feature.*
import com.fantasticthing.github.location.*
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * Created by wanbo on 2019-01-04.
 */

@KtorExperimentalLocationsAPI
fun Routing.api() {
    get<Index> {
        call.respondText("Hello World!")
    }
    get<Profile> { profile ->
        val username = profile.username
        if (username.isBlank()) {
            throw ParametersMissingException(listOf("username"))
        }
        val id = UserAuthentication().request(username)
        val response = UserProfile().request(username, id)
        call.respond(response)
    }
}