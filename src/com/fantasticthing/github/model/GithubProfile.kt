package com.fantasticthing.github.model

import com.fantasticthing.github.feature.*
import com.fantasticthing.github.helper.*
import com.fasterxml.jackson.annotation.*

/**
 * Created by wanbo on 2019-02-13.
 */
data class GithubProfile(
    val name: String,
    val login: String,
    val avatarUrl: String,
    val bio: String,
    val location: String,
    @get:JsonProperty("isDeveloperProgramMember")
    val isDeveloperProgramMember: Boolean,
    val createdAt: String,
    val email: String,
    val url: String,
    val company: String,
    val websiteUrl: String,
    val followers: Int,
    val following: Int,
    val repositories: Int,
    val contributions: Int,
    var rank: List<UserRank.Rank> = listOf(),
    val organizations: List<UserProfile.User.Organization> = listOf(),
    val pinnedRepos: List<UserProfile.User.Repo> = listOf(),
    val myRepos: List<UserProfile.User.Repo> = listOf(),
    val starRepos: List<UserProfile.User.Repo> = listOf(),
    val contributionsCounts: UserProfile.User.ContributionsCounts,
    val contributionsByMonth: List<UserProfile.User.ContributionByMonth>,
    val languageRatioByMyRepos: List<UserProfile.User.LanguageRatio>,
    val languageRatioByMyReposWithStar: List<UserProfile.User.LanguageRatio>,
    val languageRatioByStarRepos: List<UserProfile.User.LanguageRatio>,
    val languageRatioByMyReposCommit: List<UserProfile.User.LanguageRatio>,
    val commitTopRepos: List<UserProfile.User.Repo>
)

fun UserProfile.User.toGithubProfile(): GithubProfile = GithubProfile(
    this.name ?: "",
    this.login,
    this.avatarUrl,
    this.bio ?: "",
    this.location,
    this.isDeveloperProgramMember,
    this.createdAt,
    this.email,
    this.url,
    this.company,
    this.websiteUrl,
    this.followers.totalCount,
    this.following.totalCount,
    this.repositories.totalCount,
    this.contributions,
    this.rank,
    this.organizations.nodes,
    this.pinnedRepos.nodes,
    this.myRepos.nodes.subListSafe(0, 6),
    this.starRepos.nodes.subListSafe(0, 6),
    this.contributionsCounts,
    this.contributionsByMonth,
    this.languageRatioByMyRepos,
    this.languageRatioByMyReposWithStar,
    this.languageRatioByStarRepos,
    this.languageRatioByMyReposCommit,
    this.commitTopRepos
)