package com.fantasticthing.github.feature

import com.fantasticthing.github.cache.*
import com.fantasticthing.github.exception.*
import com.fantasticthing.github.helper.*
import com.fantasticthing.github.http.*
import com.fasterxml.jackson.annotation.*
import kotlinx.coroutines.*

/**
 * Created by wanbo on 2019-01-11.
 */
class UserProfile {

    private fun graphQL(): String {
        return "query(\$name: String! \$id: ID!) {\n" +
                "    user(login: \$name) {\n" +
                "        ...userInfo\n" +
                "        pinnedRepos: pinnedRepositories(first: 10) {\n" +
                "            ...pinnedRepos\n" +
                "        }\n" +
                "        myRepos: repositories(first : 100, orderBy: {\n" +
                "            direction: DESC\n" +
                "            field : STARGAZERS\n" +
                "        }) {\n" +
                "            ...myRepos\n" +
                "        }\n" +
                "        starRepos: starredRepositories(first : 100, orderBy: {\n" +
                "            direction: DESC\n" +
                "            field : STARRED_AT\n" +
                "        }) {\n" +
                "            ...starRepos\n" +
                "        }\n" +
                "        contributionsCollection {\n" +
                "            ...contributions\n" +
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
                "    followers {\n" +
                "        totalCount\n" +
                "    }\n" +
                "    following {\n" +
                "        totalCount\n" +
                "    }\n" +
                "    repositories {\n" +
                "        totalCount\n" +
                "    }\n" +
                "    organizations(first : 10) {\n" +
                "        totalCount\n" +
                "        nodes {\n" +
                "            name\n" +
                "          \tavatarUrl\n" +
                "          \turl\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "# github contributions\n" +
                "fragment contributions on ContributionsCollection {\n" +
                "    contributionCalendar {\n" +
                "        totalContributions\n" +
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
                "fragment myRepos on RepositoryConnection {\n" +
                "    totalCount\n" +
                "    nodes {\n" +
                "        name\n" +
                "        description\n" +
                "        url\n" +
                "        forkCount\n" +
                "        isFork" +
                "        stargazers {\n" +
                "            totalCount\n" +
                "        }\n" +
                "        primaryLanguage {\n" +
                "            name\n" +
                "            color\n" +
                "        }\n" +
                "        refs(first: 8, refPrefix: \"refs/heads/\") {\n" +
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
                "    }\n" +
                "}\n" +
                "\n" +
                "fragment pinnedRepos on RepositoryConnection {\n" +
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
                "}"
    }

    private fun variables(userName: String, id: String): String {
        return "{\n" +
                "  \"name\": \"$userName\",\n" +
                "  \"id\": \"$id\"\n" +
                "}"
    }

    suspend fun request(userName: String, id: String): Any {
        Cache.getUser(userName)?.also {
            return it.format()
        }

        val body =
            GraphQLRequest(
                graphQL(),
                variables(userName, id)
            ).toGraphQLBody()

        return coroutineScope {
            val task1 = async {
                client.okGraphQLRequest<GraphQLResponse<Response>>(body)
            }
            val task2 = async { UserRank().request(userName) }
            return@coroutineScope dealTask(task1.await(), task2.await())
        }
    }

    private suspend fun dealTask(userResponse: GraphQLResponse<Response>, rankResponse: UserRank.Response): User {
        userResponse.errors?.also {
            throw BadRequestException(it)
        }
        userResponse.data?.user?.also {
            return it.format().apply {
                it.rank = rankResponse.user.rankings
                Cache.putUser(this)
            }
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
        val contributionsCollection: Contributions?
    ) {

        val requestTime = System.currentTimeMillis()
        var languageRatioByMyRepos = arrayListOf<LanguageRatio>()

        data class LanguageRatio(val language: Lang, var count: Int, var ratio: Float = 0f)

        suspend fun format(): User {
            val formatMyRepos = formatMyRepos()
            if (formatMyRepos) {
                return this
            }

            throw RuntimeException()
        }

        private suspend fun formatMyRepos(): Boolean = coroutineScope {
            val allMyRepos = myRepos.totalCount
            val languageRatio = async {
                myRepos.nodes.forEach { repo ->
                    languageRatioByMyRepos.find { it.language.name == repo.primaryLanguage?.name ?: "unKnow" }?.also {
                        it.count++
                        it.ratio = (it.count.toFloat()) / allMyRepos
                    } ?: run {
                        repo.primaryLanguage?.also {
                            languageRatioByMyRepos.add(LanguageRatio(it, 1, 1f / allMyRepos))
                        } ?: run {
                            languageRatioByMyRepos.add(LanguageRatio(Lang.default(), 1, 1f / allMyRepos))
                        }

                    }
                }
                return@async true
            }
            return@coroutineScope languageRatio.await()
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
        @get:JsonProperty("isFork")
        val isFork: Boolean = false,
        val stargazers: XCount,
        val primaryLanguage: Lang?,
        val refs: RepsRefs?
    )

    data class RepsRefs(val nodes: List<RepsRef> = listOf())

    data class RepsRef(val name: String, val target: RepsRefTarget)

    data class RepsRefTarget(val history: XCount)

    data class Lang(val name: String, val color: String) {

        companion object {
            fun default(): Lang = Lang("unKnow", "#FF4A4A4A")
        }

    }

    data class Contributions(val contributionCalendar: ContributionCalendar)

    data class ContributionCalendar(
        val totalContributions: Int,
        val weeks: List<ContributionWeek> = listOf()
    )

    data class ContributionWeek(val contributionDays: List<ContributionDay> = listOf())

    data class ContributionDay(
        val color: String,
        val contributionCount: Int
    )
}