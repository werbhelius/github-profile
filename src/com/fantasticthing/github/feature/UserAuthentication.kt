package com.fantasticthing.github.feature

import com.fantasticthing.github.http.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*

/**
 * Created by wanbo on 2019-01-10.
 */

class UserAuthentication {

    private fun graphQL(): String {
        return "query(\$name: String!) {\n" +
                "    user(login: \$name) {\n" +
                "        id\n" +
                "    }\n" +
                "}"
    }

    private fun variables(userName: String): String {
        return "{\n" +
                "  \"name\": \"$userName\"\n" +
                "}"
    }

    suspend fun request(userName: String): String {
        return client.post(api) {
            body = TextContent(
                jacksonObjectMapper().writeValueAsString(
                    GraphQLRequest(
                        graphQL(),
                        variables(userName)
                    )
                ), contentType = ContentType.Application.Json
            )
        }
    }

}