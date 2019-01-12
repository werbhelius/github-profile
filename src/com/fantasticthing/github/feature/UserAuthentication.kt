package com.fantasticthing.github.feature

import com.fantasticthing.github.exception.*
import com.fantasticthing.github.helper.toGraphQLBody
import com.fantasticthing.github.http.*
import com.fantasticthing.github.model.*

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

    data class Response(val user: User)

    suspend fun request(userName: String): String {
        val body = GraphQLRequest(
            graphQL(),
            variables(userName)
        ).toGraphQLBody()

        val response = client.okRequest<GraphQLResponse<Response>>(body)
        response.errors?.also {
            throw ParametersNotFoundException("userName not found")
        }
        return response.data!!.user.id
    }

}