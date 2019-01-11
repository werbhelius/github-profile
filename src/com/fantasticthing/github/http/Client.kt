package com.fantasticthing.github.http

import com.fantasticthing.github.exception.BadRequestException
import com.fantasticthing.github.feature.GraphQLResponse
import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.content.TextContent
import io.ktor.http.HttpMethod
import java.io.*

/**
 * Created by wanbo on 2019-01-10.
 */

private val config = systemProperties() overriding
        ConfigurationProperties.fromResource("config.properties")

val host = config[Key("github.host", stringType)]
val token = config[Key("github.token", stringType)]

private val client = HttpClient(OkHttp) {
    install(JsonFeature) {
        serializer = JacksonSerializer()
    }
    engine {
        config {
            addInterceptor(HeaderInterceptor())
        }
    }
}

suspend fun okRequest(bodyJson: TextContent): GraphQLResponse {
    val response = client.post<GraphQLResponse>(host) {
        body = bodyJson
    }
    response.errors?.also {
        throw BadRequestException(response)
    }
    return response
}