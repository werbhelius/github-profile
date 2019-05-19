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
        call.respond(FreeMarkerContent("error.html", null))
    }
    get<Profile> { profile ->
        val username = profile.username
        if (username.isBlank()) {
            throw ParametersMissingException(listOf(profile.username))
        }

        Cache.getUser(username)?.also {
            call.respond(it.toGithubProfile())
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

        val loginName = Cache.getUser(username)?.let {
            launch { auth.cancel() }
            return@let it.login
        } ?: run {
            val githubProfile = UserProfile().request(username, auth.await()).toGithubProfile()
            return@run githubProfile.login
        }

        call.respondText("/profile/$loginName")
    }

}

fun Routing.staticFile() {
    val staticPath = "resources/static"
    static {
        staticBasePackage = "static"
        route("static") {
            files(File(staticPath))
            default("$staticPath/index.html")
        }
    }

}

fun Routing.handlerNotMatchedRouter() {
    route("{...}") {
        handle {
            call.respond(FreeMarkerContent("index.html", null))
        }
    }
}