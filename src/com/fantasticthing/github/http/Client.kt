package com.fantasticthing.github.http

import com.fantasticthing.github.exception.*
import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.content.*
import io.ktor.http.*

/**
 * Created by wanbo on 2019-01-10.
 */

private val config = systemProperties() overriding
        ConfigurationProperties.fromResource("config.properties")

val host = config[Key("github.host", stringType)]
val token = config[Key("github.token", stringType)]

val client = HttpClient(OkHttp) {
    install(JsonFeature) {
        serializer = JacksonSerializer()
    }
    engine {
        config {
            addInterceptor(HeaderInterceptor())
        }
    }
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified T> HttpClient.okRequest(bodyJson: TextContent): T {
    return call(host) {
        method = HttpMethod.Post
        body = bodyJson
    }.receive() as? T ?: throw InternalServerErrorException()
}