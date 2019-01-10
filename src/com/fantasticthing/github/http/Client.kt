package com.fantasticthing.github.http

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

/**
 * Created by wanbo on 2019-01-10.
 */

val api = "https://api.github.com/graphql"
val token = "2e2a0b86b65d4aa2458c42be68078e8d1314128a"

val client = HttpClient(OkHttp) {
    engine {
        config {
            addInterceptor(HeaderInterceptor())
        }
    }
}