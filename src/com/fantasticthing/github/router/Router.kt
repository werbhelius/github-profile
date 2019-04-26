package com.fantasticthing.github.router

import com.fantasticthing.github.cache.*
import com.fantasticthing.github.exception.*
import com.fantasticthing.github.feature.*
import com.fantasticthing.github.location.*
import com.fantasticthing.github.model.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by wanbo on 2019-01-04.
 */

@KtorExperimentalLocationsAPI
fun Routing.api() {
    get<Index> {
        call.respond(FreeMarkerContent("index.ftl", null))
    }
    get<Profile> { profile ->
        val username = profile.username
        if (username.isBlank()) {
            throw ParametersMissingException(listOf(profile.username))
        }

        val auth = async {
            UserAuthentication().request(username)
        }.apply { start() }

        Cache.getUser(username)?.also {
            launch { auth.cancel() }
            call.respond(it.toGithubProfile())
        } ?: run {
            val githubProfile = UserProfile().request(username, auth.await()).toGithubProfile()
            call.respond(githubProfile)
        }
    }
}