package com.fantasticthing.github.feature

import com.fantasticthing.github.http.*
import io.ktor.client.request.*

/**
 * Created by wanbo on 2019-01-15.
 */
class UserRank {

    suspend fun request(userName: String): Response  {
        var response = Response(User("", userName, "", "", ""))
        try {
            response = client.get(rank + userName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    data class Response(val user: User)

    data class User(
        val id: String,
        val login: String,
        val gravatar_url: String,
        val city: String?,
        val country: String?,
        val rankings: List<Rank> = listOf()
    )

    data class Rank(
        val language: String,
        val repository_count: String,
        val stars_count: String,
        val city_rank: String?,
        val city_count: String,
        val country_rank: String?,
        val country_count: String,
        val world_rank: String,
        val world_count: String
    )

}