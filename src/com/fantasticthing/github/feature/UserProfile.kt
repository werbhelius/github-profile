package com.fantasticthing.github.feature

import com.fantasticthing.github.exception.*
import com.fantasticthing.github.helper.*
import com.fantasticthing.github.http.*

/**
 * Created by wanbo on 2019-01-11.
 */
class UserProfile {

    private fun graphQL(): String {
        return "query(\$name: String!, \$from: DateTime!, \$to: DateTime!, \$id: ID!) {\n" +
                "    user(login: \$name) {\n" +
                "        ...userInfo\n" +
                "        pinnedRepos: pinnedRepositories(first: 10) {\n" +
                "            ...repos\n" +
                "        }\n" +
                "        myRepos: repositories(first : 100, orderBy: {\n" +
                "            direction: DESC\n" +
                "            field : STARGAZERS\n" +
                "        }) {\n" +
                "            ...repos\n" +
                "        }\n" +
                "        starRepos: starredRepositories(first : 100, orderBy: {\n" +
                "            direction: DESC\n" +
                "            field : STARRED_AT\n" +
                "        }) {\n" +
                "            ...starRepos\n" +
                "        }\n" +
                "        contributionsCollection(from: \$from, to: \$to) {\n" +
                "            ...contributions\n" +
                "        }\n" +
                "        reposCommit: repositories(first:100, orderBy: {\n" +
                "            direction: DESC\n" +
                "            field: STARGAZERS\n" +
                "        }) {\n" +
                "            ...reposCommit\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "# 个人信息\n" +
                "fragment userInfo on User {\n" +
                "    id\n" +
                "    name\n" +
                "    login\n" +
                "    avatarUrl\n" +
                "    bio\n" +
                "    location\n" +
                "    company\n" +
                "    createdAt\n" +
                "    email\n" +
                "    url\n" +
                "    websiteUrl\n" +
                "    isDeveloperProgramMember\n" +
                "    followers {\n" +
                "        totalCount\n" +
                "    }\n" +
                "    following {\n" +
                "        totalCount\n" +
                "    }\n" +
                "    repositories {\n" +
                "        totalCount\n" +
                "    }\n" +
                "    organizations(first : 100) {\n" +
                "        totalCount\n" +
                "        nodes {\n" +
                "            name\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "# github contributions\n" +
                "fragment contributions on ContributionsCollection {\n" +
                "    contributionCalendar {\n" +
                "        totalContributions\n" +
                "        colors\n" +
                "        months {\n" +
                "            name\n" +
                "            totalWeeks\n" +
                "        }\n" +
                "        weeks {\n" +
                "            contributionDays {\n" +
                "                color\n" +
                "                contributionCount\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "# pinned and me repos\n" +
                "fragment repos on RepositoryConnection {\n" +
                "    totalCount\n" +
                "    nodes {\n" +
                "        name\n" +
                "        description\n" +
                "        url\n" +
                "        forkCount\n" +
                "        stargazers {\n" +
                "            totalCount\n" +
                "        }\n" +
                "        primaryLanguage {\n" +
                "            name\n" +
                "            color\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "# star repos\n" +
                "fragment starRepos on StarredRepositoryConnection {\n" +
                "    totalCount\n" +
                "    nodes {\n" +
                "        name\n" +
                "        description\n" +
                "        url\n" +
                "        forkCount\n" +
                "        stargazers {\n" +
                "            totalCount\n" +
                "        }\n" +
                "        primaryLanguage {\n" +
                "            name\n" +
                "            color\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "# commit id: MDQ6VXNlcjEyNzYzMjc3\n" +
                "fragment reposCommit on RepositoryConnection {\n" +
                "    nodes {\n" +
                "        name\n" +
                "        refs(first: 100, refPrefix: \"refs/heads/\") {\n" +
                "            nodes {\n" +
                "                name\n" +
                "                target {\n" +
                "                ... on Commit {\n" +
                "                        history(first: 0, author: {id : \$id}) {\n" +
                "                            totalCount\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "    \t\t}\n" +
                "\t\t}\n" +
                "}"
    }

    private fun variables(userName: String, id: String, fromTime: String, toTime: String): String {
        return "{\n" +
                "  \"name\": \"$userName\",\n" +
                "  \"from\": \"$fromTime\",\n" +
                "  \"to\": \"$toTime\",\n" +
                "  \"id\": \"$id\"\n" +
                "}"
    }

    suspend fun request(userName: String, id: String): Any {
        val fromAndToTime = getFromAndToTime()
        val body =
            GraphQLRequest(graphQL(), variables(userName, id, fromAndToTime.first, fromAndToTime.second)).toGraphQLBody()
        val response = client.okRequest<GraphQLResponse<Any>>(body)
        response.errors?.also {
            throw BadRequestException(it)
        }
        return response
    }

}