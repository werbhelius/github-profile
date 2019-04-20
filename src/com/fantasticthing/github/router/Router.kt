package com.fantasticthing.github.router

import com.fantasticthing.github.cache.*
import com.fantasticthing.github.exception.*
import com.fantasticthing.github.feature.*
import com.fantasticthing.github.location.*
import com.fantasticthing.github.model.*
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
            throw ParametersMissingException(listOf(profile.username))
        }

        Cache.getUser(username)?.also {
            call.respond(it.toGithubProfile())
        } ?: run {
            val id = UserAuthentication().request(username)
            val githubProfile = UserProfile().request(username, id).toGithubProfile()
            call.respond(githubProfile)
        }
    }
}