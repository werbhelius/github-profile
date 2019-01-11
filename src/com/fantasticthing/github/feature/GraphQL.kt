package com.fantasticthing.github.feature

/**
 * Created by wanbo on 2019-01-10.
 */
data class GraphQLRequest(val query: String, val variables: String)

data class GraphQLResponse <T> (val data: T?, val errors: Any?)