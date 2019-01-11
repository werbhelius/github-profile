package com.fantasticthing.github.http

import com.fantasticthing.github.feature.*
import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.content.*

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
    return client.post(host) {
        body = bodyJson
    }
}