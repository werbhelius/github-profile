package com.fantasticthing.github.http

import com.fantasticthing.github.exception.InternalServerErrorException
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.call.receive
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.content.TextContent
import io.ktor.http.HttpMethod
import okhttp3.Protocol

/**
 * Created by wanbo on 2019-01-10.
 */

private val config = systemProperties() overriding
        ConfigurationProperties.fromResource("config.properties")

val host = config[Key("github.host", stringType)]
val rank = config[Key("github.rank", stringType)]
val token = config[Key("github.token", stringType)]

val client = HttpClient(OkHttp) {
    install(JsonFeature) {
        serializer = JacksonSerializer()
    }
    engine {
        config {
            protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            addInterceptor(HeaderInterceptor())
        }
    }
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified T> HttpClient.okGraphQLRequest(bodyJson: TextContent): T {
    return call(host) {
        method = HttpMethod.Post
        body = bodyJson
    }.receive() as? T ?: throw InternalServerErrorException()
}