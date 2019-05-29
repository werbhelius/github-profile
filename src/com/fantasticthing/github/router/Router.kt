package com.fantasticthing.github.router

import com.fantasticthing.github.cache.*
import com.fantasticthing.github.exception.*
import com.fantasticthing.github.feature.*
import com.fantasticthing.github.location.*
import com.fantasticthing.github.model.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.request.header
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by wanbo on 2019-01-04.
 */

@KtorExperimentalLocationsAPI
fun Routing.api() {

    // Render
    get<Index> {
        call.respond(FreeMarkerContent("index.ftl", null))
    }
    get<Profile> { profile ->
        val username = profile.username
        if (username.isBlank()) {
            throw ParametersMissingException(listOf(profile.username))
        }

        Cache.getUser(username)?.also {
            call.respond(FreeMarkerContent("profile.ftl", it.toGithubProfile()))
        } ?: run {
            call.respond(FreeMarkerContent("index.ftl", null))
        }
    }

    // API
    get<User> { user ->
        val username = user.username
        if (username.isBlank()) {
            throw ParametersMissingException(listOf(user.username))
        }

        val auth = async {
            UserAuthentication().request(username)
        }.apply { start() }

        val githubProfile = Cache.getUser(username)?.let {
            launch { auth.cancel() }
            return@let it.toGithubProfile()
        } ?: run {
            return@run UserProfile().request(username, auth.await()).toGithubProfile()
        }

        if (call.request.header("client") == "web") {
            call.respondText("/profile/${githubProfile.login}")
        } else {
            call.respond(githubProfile)
        }
    }

}

fun Routing.staticFile() {
    static("static") {
        static("js") {
            resources("static/js")
        }
        static("css") {
            resources("static/css")
        }
        static("fonts") {
            resources("static/fonts")
        }
        static("svg") {
            resources("static/svg")
        }
    }
}

fun Routing.handlerNotMatchedRouter() {
    route("{...}") {
        handle {
            call.respond(FreeMarkerContent("index.ftl", null))
        }
    }
}