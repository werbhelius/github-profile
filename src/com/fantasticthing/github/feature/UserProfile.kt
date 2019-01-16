package com.fantasticthing.github.feature

import com.fantasticthing.github.cache.*
import com.fantasticthing.github.exception.*
import com.fantasticthing.github.helper.*
import com.fantasticthing.github.http.*
import kotlinx.coroutines.*

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
                "# UserInfo\n" +
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
                "# commit id: XXX\n" +
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
        Cache.getUser(userName)?.also {
            return it.format()
        }

        val fromAndToTime = getFromAndToTime()
        val body =
            GraphQLRequest(
                graphQL(),
                variables(userName, id, fromAndToTime.first, fromAndToTime.second)
            ).toGraphQLBody()

        return coroutineScope {
            val task1 = async {
                client.okGraphQLRequest<GraphQLResponse<Response>>(body)
            }
            val task2 = async { UserRank().request(userName) }
            return@coroutineScope dealTask(task1.await(), task2.await())
        }
    }

    private fun dealTask(userResponse: GraphQLResponse<Response>, rankResponse: UserRank.Response): User {
        userResponse.errors?.also {
            throw BadRequestException(it)
        }
        userResponse.data?.user?.also {
            it.rank = rankResponse.user.rankings
            Cache.putUser(it)
            return it.format()
        }
    }

    data class Response(val user: User)

    data class User(
        val id: String,
        val name: String,
        val login: String,
        val avatarUrl: String,
        val bio: String,
        val location: String,
        val createdAt: String,
        val email: String,
        val url: String,
        val company: String,
        val websiteUrl: String,
        val followers: XCount,
        val following: XCount,
        val repositories: XCount,
        var rank: List<UserRank.Rank> = listOf(),
        val organizations: Organizations,
        val pinnedRepos: Repos,
        val myRepos: Repos,
        val starRepos: Repos,
        val contributionsCollection: Contributions?,
        val reposCommit: ReposCommit
    ) {

        fun format(): User {
            return this
        }

    }

    data class XCount(val totalCount: Int)

    data class Organizations(val totalCount: Int, val nodes: List<Organization> = listOf())

    data class Organization(val name: String)

    data class Repos(val totalCount: Int, val nodes: List<Repo> = listOf())

    data class Repo(
        val name: String,
        val description: String?,
        val url: String,
        val forkCount: Int,
        val stargazers: XCount,
        val primaryLanguage: Lang?
    )

    data class ReposCommit(val nodes: List<RepoCommit> = listOf())

    data class RepoCommit(
        val name: String,
        val refs: RepsRefs?
    )

    data class RepsRefs(val nodes: List<RepsRef> = listOf())

    data class RepsRef(val name: String, val target: RepsRefTarget)

    data class RepsRefTarget(val history: XCount)

    data class Lang(val name: String, val color: String)

    data class Contributions(val contributionCalendar: ContributionCalendar)

    data class ContributionCalendar(
        val totalContributions: Int,
        val colors: List<String> = listOf(),
        val months: List<ContributionMonth> = listOf(),
        val weeks: List<ContributionWeek> = listOf()
    )

    data class ContributionMonth(
        val name: String,
        val totalWeeks: Int
    )

    data class ContributionWeek(val contributionDays: List<ContributionDay> = listOf())

    data class ContributionDay(
        val color: String,
        val contributionCount: Int
    )
}