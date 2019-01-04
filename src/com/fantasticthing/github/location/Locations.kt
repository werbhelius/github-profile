package com.fantasticthing.github.location

import io.ktor.locations.*

/**
 * Created by wanbo on 2019-01-04.
 */

@Location("/")
class Index

@Location("/profile")
class Profile(val username: String = "")