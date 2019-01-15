package com.fantasticthing.github.feature

import com.fantasticthing.github.exception.*
import com.fantasticthing.github.helper.toGraphQLBody
import com.fantasticthing.github.http.*

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
        val body = GraphQLRequest(
            graphQL(),
            variables(userName)
        ).toGraphQLBody()

        val response = client.okGraphQLRequest<GraphQLResponse<Response>>(body)
        response.errors?.also {
            throw ParametersNotFoundException("userName not found")
        }
        response.data?.user?.id?.also {
            return it
        }
    }

    data class Response(val user: User?)

    data class User(val id: String)

}