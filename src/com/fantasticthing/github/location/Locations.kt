package com.fantasticthing.github.location

import io.ktor.locations.*

/**
 * Created by wanbo on 2019-01-04.
 */

@KtorExperimentalLocationsAPI
@Location("/")
class Index

@KtorExperimentalLocationsAPI
@Location("/profile/{username}")
class Profile(val username: String = "")

@KtorExperimentalLocationsAPI
@Location("/api/user")
class User(val username: String = "")