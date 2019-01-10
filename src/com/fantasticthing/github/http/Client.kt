package com.fantasticthing.github.http

import com.fantasticthing.github.exception.BadRequestException
import com.fantasticthing.github.feature.GraphQLResponse
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.content.TextContent
import io.ktor.http.HttpMethod

/**
 * Created by wanbo on 2019-01-10.
 */

val api = "https://api.github.com/graphql"
val token = "2e2a0b86b65d4aa2458c42be68078e8d1314128a"

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
    val response = client.post<GraphQLResponse>(api) {
        method = HttpMethod.Post
        body = bodyJson
    }
    response.errors?.also {
        throw BadRequestException(response)
    }
    return response
}