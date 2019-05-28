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
@Suppress("DeferredIsResult")
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
                "    isDeveloperProgramMember\n" +
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
                "fragment myRepos on RepositoryConnection {\n" +
                "    totalCount\n" +
                "    nodes {\n" +
                "        nameWithOwner\n" +
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
                "        nameWithOwner\n" +
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
                "        nameWithOwner\n" +
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

    suspend fun request(userName: String, id: String): User {
        val body =
            GraphQLRequest(
                graphQL(),
                variables(userName, id)
            ).toGraphQLBody()

        return coroutineScope {
            val task1 = async {
                client.okGraphQLRequest<GraphQLResponse<Response>>(body)
            }.apply { start() }
            val task2 = async { UserRank().request(userName) }.apply { start() }
            return@coroutineScope dealTask(task1.await(), task2.await())
        }
    }

    private suspend fun dealTask(userResponse: GraphQLResponse<Response>, rankResponse: UserRank.Response): User {
        userResponse.errors?.also {
            throw BadRequestException(it)
        }
        userResponse.data?.user?.also {
            return it.format().apply {
                it.city = rankResponse.user.city ?: ""
                it.country = rankResponse.user.country ?: ""
                it.rank = rankResponse.user.rankings
                Cache.putUser(this)
            }
        }

        throw InternalServerErrorException()
    }

    data class Response(val user: User)

    data class User(
        val id: String,
        val name: String? = "",
        val login: String,
        val avatarUrl: String,
        val bio: String? = "",
        val location: String? = "",
        @get:JsonProperty("isDeveloperProgramMember")
        val isDeveloperProgramMember: Boolean,
        val createdAt: String,
        val email: String,
        val url: String,
        val company: String,
        val websiteUrl: String? = "",
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
        var city = "no place"
        var country = "no place"
        var contributions = 0
        var contributionsByMonth = arrayListOf<ContributionByMonth>()
        var contributionsCounts = ContributionsCounts(0, arrayListOf())
        var languageRatioByMyRepos = arrayListOf<LanguageRatio>()
        var languageRatioByMyReposWithStar = arrayListOf<LanguageRatio>()
        var languageRatioByStarRepos = arrayListOf<LanguageRatio>()
        var languageRatioByMyReposCommit = arrayListOf<LanguageRatio>()
        var commitTopRepos = arrayListOf<Repo>()

        data class LanguageRatio(val language: Lang, var count: Int, var ratio: Float = 0f)

        data class ContributionByMonth(val name: String, var count: Int = 0, var ratio: Float = 0f, val totalCount: Int)

        data class ContributionsCounts(var maxCount: Int, var counts: MutableList<Int>)

        suspend fun format(): User = coroutineScope {
            val formatMyRepos = formatMyRepos().apply { start() }
            val formatStarRepos = formatStarRepos().apply { start() }
            val formatContributionsWithMonth = formatContributionsWithMonth().apply { start() }
            val formatCommitByMyRepos = formatCommitByMyRepos().apply { start() }
            if (formatMyRepos.await() && formatStarRepos.await() && formatContributionsWithMonth.await() && formatCommitByMyRepos.await()) {
                return@coroutineScope this@User
            } else {
                throw InternalServerErrorException()
            }
        }

        private fun formatMyRepos(): Deferred<Boolean> = GlobalScope.async(Dispatchers.IO) {
            launch {
                val allMyRepos = myRepos.nodes.size
                myRepos.nodes.forEach { repo ->
                    languageRatioByMyRepos.find { it.language.name == repo.primaryLanguage?.name ?: "UnKnow" }?.also {
                        it.count++
                        it.ratio = ((it.count.toFloat()) / allMyRepos).format()
                    } ?: run {
                        repo.primaryLanguage?.also {
                            languageRatioByMyRepos.add(LanguageRatio(it, 1, (1f / allMyRepos).format()))
                        } ?: run {
                            languageRatioByMyRepos.add(LanguageRatio(Lang.default(), 1, (1f / allMyRepos).format()))
                        }

                    }
                }
                languageRatioByMyRepos.sortByDescending { it.count }
            }
            launch {
                val allMyReposStarCount = myRepos.nodes.sumBy { it.stargazers.totalCount }
                myRepos.nodes.filter { !it.isFork }.forEach { repo ->
                    languageRatioByMyReposWithStar.find { it.language.name == repo.primaryLanguage?.name ?: "UnKnow" }?.also {
                        it.count = it.count + repo.stargazers.totalCount
                        it.ratio = ((it.count.toFloat()) / allMyReposStarCount).format()
                    } ?: run {
                        repo.primaryLanguage?.also {
                            languageRatioByMyReposWithStar.add(
                                LanguageRatio(
                                    it,
                                    repo.stargazers.totalCount,
                                    (repo.stargazers.totalCount.toFloat() / allMyReposStarCount).format()
                                )
                            )
                        } ?: run {
                            languageRatioByMyReposWithStar.add(
                                LanguageRatio(
                                    Lang.default(),
                                    repo.stargazers.totalCount,
                                    (repo.stargazers.totalCount.toFloat() / allMyReposStarCount).format()
                                )
                            )
                        }

                    }
                }
                languageRatioByMyReposWithStar.sortByDescending { it.count }
            }

            return@async true
        }

        private fun formatStarRepos(): Deferred<Boolean> = GlobalScope.async(Dispatchers.IO) {
            val allStarRepos = starRepos.nodes.size
            starRepos.nodes.forEach { repo ->
                languageRatioByStarRepos.find { it.language.name == repo.primaryLanguage?.name ?: "UnKnow" }?.also {
                    it.count++
                    it.ratio = ((it.count.toFloat()) / allStarRepos).format()
                } ?: run {
                    repo.primaryLanguage?.also {
                        languageRatioByStarRepos.add(LanguageRatio(it, 1, (1f / allStarRepos).format()))
                    } ?: run {
                        languageRatioByStarRepos.add(LanguageRatio(Lang.default(), 1, (1f / allStarRepos).format()))
                    }

                }
            }
            languageRatioByStarRepos.sortByDescending { it.count }
            return@async true
        }

        private fun formatContributionsWithMonth(): Deferred<Boolean> = GlobalScope.async(Dispatchers.IO) {
            var start = 0
            var end = 0
            var maxCount = 0
            contributions = contributionsCollection?.contributionCalendar?.totalContributions ?: 0
            contributionsCollection?.contributionCalendar?.months?.forEach { month ->
                end += month.totalWeeks
                val contributionByMonth = ContributionByMonth(
                    month.name,
                    totalCount = contributionsCollection.contributionCalendar.totalContributions
                )
                (start until end).forEach { index ->
                    contributionByMonth.count += contributionsCollection.contributionCalendar.weeks[index].contributionDays.sumBy { it.contributionCount }
                    contributionByMonth.ratio = contributionByMonth.count.toFloat() / contributionByMonth.totalCount
                }
                start = end
                if (contributionByMonth.count >= maxCount) {
                    maxCount = contributionByMonth.count
                }
                contributionsByMonth.add(contributionByMonth)
            }

            if (maxCount <= 20) {
                contributionsCounts.maxCount = maxCount
                contributionsCounts.counts.add(maxCount)
            } else {
                maxCount = Math.round((maxCount / 5f) / 10) * 10
                contributionsCounts.maxCount = maxCount * 5
                repeat(6) {
                    contributionsCounts.counts.add(0, 0 + maxCount * it)
                }
            }

            return@async true
        }

        private fun formatCommitByMyRepos(): Deferred<Boolean> = GlobalScope.async(Dispatchers.IO) {
            launch {
                val allCommitCount = myRepos.nodes.filter { !it.isFork }
                    .sumBy { repo -> repo.refs?.nodes?.sumBy { it.target.history.totalCount } ?: 0 }
                myRepos.nodes.filter { !it.isFork }.forEach { repo ->
                    repo.commitCount = repo.refs?.nodes?.sumBy { it.target.history.totalCount } ?: 0
                    repo.commitRadio = (repo.commitCount.toFloat() / allCommitCount).format()
                    languageRatioByMyReposCommit.find { it.language.name == repo.primaryLanguage?.name ?: "UnKnow" }?.also {
                        it.count = it.count + repo.commitCount
                        it.ratio = ((it.count.toFloat()) / allCommitCount).format()
                    } ?: run {
                        repo.primaryLanguage?.also {
                            languageRatioByMyReposCommit.add(
                                LanguageRatio(
                                    it,
                                    repo.commitCount,
                                    (repo.commitCount.toFloat() / allCommitCount).format()
                                )
                            )
                        } ?: run {
                            languageRatioByMyReposCommit.add(
                                LanguageRatio(
                                    Lang.default(),
                                    repo.commitCount,
                                    (repo.commitCount.toFloat() / allCommitCount).format()
                                )
                            )
                        }
                    }
                }
                languageRatioByMyReposCommit.sortByDescending { it.count }
                commitTopRepos.addAll(myRepos.nodes.sortedByDescending { it.commitCount })
            }
            return@async true
        }

        data class XCount(val totalCount: Int)

        data class Organizations(val totalCount: Int, val nodes: List<Organization> = listOf())

        data class Organization(val name: String, val avatarUrl: String, val url: String)

        data class Repos(val totalCount: Int, val nodes: List<Repo> = listOf())

        data class Repo(
            val nameWithOwner: String,
            val description: String?,
            val url: String,
            val forkCount: Int,
            @get:JsonProperty("isFork")
            val isFork: Boolean = false,
            val stargazers: XCount,
            var commitCount: Int = 0,
            var commitRadio: Float = 0f,
            val primaryLanguage: Lang?,
            val refs: RepsRefs?
        )

        data class RepsRefs(val nodes: List<RepsRef> = listOf())

        data class RepsRef(val name: String, val target: RepsRefTarget)

        data class RepsRefTarget(val history: XCount)

        data class Lang(val name: String, val color: String?) {

            companion object {
                fun default(): Lang = Lang("UnKnow", "#FF4A4A4A")
            }

        }

        data class Contributions(val contributionCalendar: ContributionCalendar)

        data class ContributionCalendar(
            val totalContributions: Int,
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
}