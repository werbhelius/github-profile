<#import "temp.ftl" as layout />
<!DOCTYPE html>
<html lang="en">
<@layout.head "${login}'s GitHub Profile Summary"/>
<body>
<div id="index-page">
    <@layout.header "${login}"/>
    <div id="profile-page" class="profile-page">
        <div class="profile-user-info-div">
            <div id="user-info" class="profile-user-info">
                <div id="user-detail" class="user-detail">
                    <a target="_blank" class="avatar-a" href="https://github.com/${login}">
                        <img class="user-img" src="${avatarUrl}">
                    </a>
                    <p class="user-name"><a target="_blank" href="https://github.com/${login}">${login}(${name})</a></p>
                    <p class="user-bio">${bio}</p>
                    <div class="flex">
                        <div class="user-friends">
                            <img src="../static/svg/follower.svg">
                            <div class="user-friends-detail">
                                <p class="user-friends-count">${followers}</p>
                                <p class="user-friends-name">Followers</p>
                            </div>
                        </div>
                        <div class="user-friends">
                            <img class="no-margin" src="../static/svg/following.svg">
                            <div class="user-friends-detail">
                                <p class="user-friends-count">${following}</p>
                                <p class="user-friends-name">Following</p>
                            </div>
                        </div>
                        <div class="user-friends">
                            <img src="../static/svg/repositories.svg">
                            <div class="user-friends-detail">
                                <p class="user-friends-count">${repositories}</p>
                                <p class="user-friends-name">Repositories</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="user-contributions" class="user-contributions">
                    <p class="user-contributions-count">${contributions} contributions in the last year</p>
                    <div class="user-contributions-map">
                        <div>
                            <#list contributionsCounts.counts as level>
                                <p class="user-contributions-max-count">${level}</p>
                            </#list>
                        </div>
                        <div class="user-contributions-chart">
                            <#list contributionsByMonth as con>
                                <div>
                                    <#assign maxCount = contributionsCounts.maxCount />
                                    <#if maxCount != 0>
                                        <img class="dot margin-3"
                                             style="margin-top: ${155 - (con.count/maxCount* 155)}px"
                                             src="../static/svg/dot.svg">
                                    </#if>

                                </div>
                            </#list>
                        </div>
                        <p class="user-contributions-year">2019</p>
                    </div>
                    <div class="user-contributions-month">
                        <#list contributionsByMonth as con>
                            <p class="user-contributions-month-text">${con.name}</p>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
        <div class="profile-repos-div">
            <div>
                <div class="profile-more">
                    <p class="content-title">More</p>
                    <div style="margin-top: 30px">
                        <p class="more-content">Company : ${company}</p>
                        <p class="more-content">Location : ${location}</p>
                        <p class="more-content">Email : <a class="a-link" target="_blank"
                                                           href="mailto:${email}">${email} </a></p>
                        <p class="more-content">Website : <a class="a-link" target="_blank"
                                                             href="${websiteUrl}">${websiteUrl}</a></p>
                    </div>
                </div>
                <div class="profile-more">
                    <p class="content-title" style="margin-top: 50px">Organizations</p>
                    <div style="padding-top: 10px">
                        <#if (organizations?size) gt 0>
                            <#list organizations as org>
                                <div class="organizations-div">
                                    <img class="organizations-img" src="${org.avatarUrl}">
                                    <p class="organizations-name">${org.name}</p>
                                </div>
                            </#list>
                        <#else >
                            <p class="more-content">You haven't joined any organizations!</p>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="profile-repos-block" id="pinned-repos">
                <p class="content-title ">Pinned Repos</p>
                <#if pinnedRepos?size != 0>
                    <div class="repos-bg">
                        <#list pinnedRepos as repos>
                            <div class="repo">
                                <p class="repos-name"><a class="a-repos" target="_blank"
                                                         href="https://github.com/${repos.nameWithOwner}">${repos.nameWithOwner}</a>
                                </p>
                                <p class="repos-desc">${repos.description!}</p>
                                <div class="repos-star">
                                    <div class="repos-lang"
                                         style="background-color:${(repos.primaryLanguage.color)!'#262626'};"></div>
                                    <p class="repos-star-text">${(repos.primaryLanguage.name)!'Unknow'}</p>
                                    <img src="../static/svg/star.svg">
                                    <p class="repos-star-text">${repos.stargazers.totalCount}</p>
                                    <img src="../static/svg/fork.svg">
                                    <p class="repos-star-text">${repos.forkCount}</p>
                                </div>
                            </div>
                            <#if !repos?is_last>
                                <div class="repos-line"></div>
                            </#if>
                        </#list>
                    </div>
                </#if>
            </div>
            <div class="profile-repos-block">
                <p class="content-title ">Top Star Repos</p>
                <#if myRepos?size != 0>
                    <div class="repos-bg">
                        <#list myRepos as repos>
                            <div class="repo">
                                <p class="repos-name"><a class="a-repos" target="_blank"
                                                         href="https://github.com/${repos.nameWithOwner}">${repos.nameWithOwner}</a>
                                </p>
                                <p class="repos-desc">${repos.description!}</p>
                                <div class="repos-star">
                                    <div class="repos-lang"
                                         style="background-color:${(repos.primaryLanguage.color)!'#262626'}"></div>
                                    <p class="repos-star-text">${(repos.primaryLanguage.name)!'Unknow'}</p>
                                    <img src="../static/svg/star.svg">
                                    <p class="repos-star-text">${repos.stargazers.totalCount}</p>
                                    <img src="../static/svg/fork.svg">
                                    <p class="repos-star-text">${repos.forkCount}</p>
                                </div>
                            </div>
                            <#if !repos?is_last>
                                <div class="repos-line"></div>
                            </#if>
                        </#list>
                    </div>
                </#if>
            </div>
        </div>
        <#if rank?size != 0>
            <div class="profile-lang-div">
                <div class="profile-lang-rank">
                    <p class="content-title">Top Star Language</p>
                    <div class="lang-bg">
                        <#list rank as rn>
                            <#if rn?index < 4>
                                <div class="lang-detail" <#if rn?index == 0 || rn?index == 2> style="background-color: #ffffff" </#if>>
                                    <#if rn?index == 0>
                                        <div class="lang-rank-bg" style="background-color: #FFBD43">1</div>
                                    </#if>
                                    <#if rn?index == 1>
                                        <div class="lang-rank-bg" style="background-color: #C7C7C7">2</div>
                                    </#if>
                                    <#if rn?index == 2>
                                        <div class="lang-rank-bg" style="background-color: #CD8989">3</div>
                                    </#if>
                                    <#if rn?index == 3>
                                        <div class="lang-rank-bg-without-shadow" style="color: #F18E33">4</div>
                                    </#if>
                                    <p class="lang-rank-lang">${rn.language}</p>
                                    <img style="margin-left: 14px" src="../static/svg/code-small.svg">
                                    <p class="lang-rank-count">${rn.repository_count}</p>
                                    <img style="margin-left: 14px" src="../static/svg/star-small.svg">
                                    <p class="lang-rank-count">${rn.stars_count}</p>
                                </div>
                            </#if>
                        </#list>
                    </div>
                </div>
                <div class="profile-lang-world">
                    <p class="content-title">Top Language Rank</p>
                    <div class="profile-lang-world-list">
                        <#list rank as rn>
                            <div class="lang-world-bg">
                                <p class="lang-world-text">${rn.language}</p>
                                <div class="lang-world">
                                    <img class="lang-img" src="../static/svg/city.svg">
                                    <p class="lang-world-city-text">${(city)}</p>
                                    <p class="lang-world-rank-text"><span
                                                class="lang-world-rank-text-high">${(rn.city_rank)!'1'}</span>
                                        / ${(rn.city_count)!'1'}</p>
                                </div>
                                <div class="lang-world">
                                    <img class="lang-img" src="../static/svg/country.svg">
                                    <p class="lang-world-city-text">${(country)}</p>
                                    <p class="lang-world-rank-text"><span
                                                class="lang-world-rank-text-high">${(rn.country_rank)!'1'}</span>
                                        / ${(rn.country_count)!'1'}</p>
                                </div>
                                <div class="lang-world">
                                    <img class="lang-img" src="../static/svg/world.svg">
                                    <p class="lang-world-city-text">Worldwide</p>
                                    <p class="lang-world-rank-text"><span
                                                class="lang-world-rank-text-high">${(rn.world_rank)!'1'}</span>
                                        / ${(rn.world_count)!'1'}
                                    </p>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </#if>
        <div class="profile-chart">
            <div class="profile-chart-div">
                <div class="profile-chart-1">
                    <div class="profile-chart-detail">
                        <p class="content-title">My Repos per Language</p>
                        <div class="pie" id="languageRatioByMyRepos">
                            <svg class="pie-svg" onload="calculateXY(this, 'languageRatioByMyRepos')">
                                <#assign r = 0>
                                <#assign bgColor = "#21A25A">
                                <#list languageRatioByMyRepos as langRatio >
                                    <#if langRatio?index == 1 || langRatio?index == 4>
                                        <#assign bgColor = "#2A3139">
                                    </#if>

                                    <#if langRatio?index == 3 || langRatio?index == 6>
                                        <#assign bgColor = "#424D55">
                                    </#if>

                                    <#if langRatio?index == 2 || langRatio?index == 5>
                                        <#assign bgColor = "#303840">
                                    </#if>


                                    <#assign rotate = 360 * r>
                                    <#assign start = 700*langRatio.ratio>
                                    <#assign end = 700*(1-langRatio.ratio)>

                                    <#if langRatio?is_last>
                                        <#assign start = 700*((1-r))>
                                        <#assign end = 700*(r)>
                                    </#if>

                                    <#if r lt 0.99>
                                        <circle class="pie-1" r="111.5" cx="142.5" cy="142.5" stroke="${bgColor}"
                                                stroke-dasharray="${start} ${end}"
                                                transform="rotate(${rotate}, 142.5,142.5)"
                                                onmouseover="displayTip('languageRatioByMyRepos', ${langRatio?index})"></circle>
                                    </#if>
                                    <#assign r = r + langRatio.ratio>
                                </#list>
                                <#assign angle = 0>
                                <#assign r = 0>
                                <#list languageRatioByMyRepos as langRatio >
                                    <#assign r = angle + langRatio.ratio / 2>
                                    <#assign angle = angle + langRatio.ratio>
                                    <#assign ratio = langRatio.ratio>
                                    <#assign name = langRatio.language.name>
                                    <#if langRatio?is_last && angle lt 1>
                                        <#assign r = angle + (1- angle) / 2>
                                        <#assign ratio = (1- angle)>
                                        <#assign name = "Other">
                                    </#if>
                                    <#if ratio gt 0.002>
                                        <text class="pie-text-percent" data-angle="${(360 * r - 90)}"
                                              data-name="${name}"
                                              fill="#ffffff"
                                              transform="rotate(90, 142.5,142.5)">${ratio * 100}%
                                        </text>
                                    </#if>
                                </#list>
                            </svg>
                        </div>
                    </div>
                    <div class="profile-chart-detail" style="margin: 0 auto">
                        <p class="content-title">My Repos Stars per Language</p>
                        <div class="pie" id="languageRatioByMyReposWithStar">
                            <svg class="pie-svg" onload="calculateXY(this, 'languageRatioByMyReposWithStar')">
                                <#assign r = 0>
                                <#assign bgColor = "#C9C34E">
                                <#list languageRatioByMyReposWithStar as langRatio >
                                    <#if langRatio?index == 1 || langRatio?index == 4>
                                        <#assign bgColor = "#2A3139">
                                    </#if>

                                    <#if langRatio?index == 3 || langRatio?index == 6>
                                        <#assign bgColor = "#424D55">
                                    </#if>

                                    <#if langRatio?index == 2 || langRatio?index == 5>
                                        <#assign bgColor = "#303840">
                                    </#if>


                                    <#assign rotate = 360 * r>
                                    <#assign start = 700*langRatio.ratio>
                                    <#assign end = 700*(1-langRatio.ratio)>

                                    <#if langRatio?is_last>
                                        <#assign start = 700*((1-r))>
                                        <#assign end = 700*(r)>
                                    </#if>

                                    <#if r lt 0.99>
                                        <circle class="pie-1" r="111.5" cx="142.5" cy="142.5" stroke="${bgColor}"
                                                stroke-dasharray="${start} ${end}"
                                                transform="rotate(${rotate}, 142.5,142.5)"
                                                onmouseover="displayTip('languageRatioByMyReposWithStar', ${langRatio?index})"></circle>
                                    </#if>

                                    <#assign r = r + langRatio.ratio>
                                </#list>
                                <#assign angle = 0>
                                <#assign r = 0>
                                <#list languageRatioByMyReposWithStar as langRatio >
                                    <#assign r = angle + langRatio.ratio / 2>
                                    <#assign angle = angle + langRatio.ratio>
                                    <#assign ratio = langRatio.ratio>
                                    <#assign name = langRatio.language.name>
                                    <#if langRatio?is_last && angle lt 1>
                                        <#assign r = angle + (1- angle) / 2>
                                        <#assign ratio = (1- angle)>
                                        <#assign name = "Other">
                                    </#if>
                                    <#if ratio gt 0.002>
                                        <text class="pie-text-percent" data-angle="${(360 * r - 90)}"
                                              data-name="${name}"
                                              fill="#ffffff"
                                              transform="rotate(90, 142.5,142.5)">${ratio * 100}%
                                        </text>
                                    </#if>
                                </#list>
                            </svg>
                        </div>
                    </div>
                    <div class="profile-chart-detail" style="margin-right: auto">
                        <p class="content-title">My Repos Commits per Language</p>
                        <div class="pie" id="languageRatioByMyReposCommit">
                            <svg class="pie-svg" onload="calculateXY(this, 'languageRatioByMyReposCommit')">
                                <#assign r = 0>
                                <#assign bgColor = "#4C5FBA">
                                <#list languageRatioByMyReposCommit as langRatio >
                                    <#if langRatio?index == 1 || langRatio?index == 4>
                                        <#assign bgColor = "#2A3139">
                                    </#if>

                                    <#if langRatio?index == 3 || langRatio?index == 6>
                                        <#assign bgColor = "#424D55">
                                    </#if>

                                    <#if langRatio?index == 2 || langRatio?index == 5>
                                        <#assign bgColor = "#303840">
                                    </#if>


                                    <#assign rotate = 360 * r>
                                    <#assign start = 700*langRatio.ratio>
                                    <#assign end = 700*(1-langRatio.ratio)>

                                    <#if langRatio?is_last>
                                        <#assign start = 700*((1-r))>
                                        <#assign end = 700*(r)>
                                    </#if>
                                    <#if r lt 0.99>
                                        <circle class="pie-1" r="111.5" cx="142.5" cy="142.5" stroke="${bgColor}"
                                                stroke-dasharray="${start} ${end}"
                                                transform="rotate(${rotate}, 142.5,142.5)"
                                                onmouseover="displayTip('languageRatioByMyReposCommit', ${langRatio?index})"></circle>
                                    </#if>
                                    <#assign r = r + langRatio.ratio>
                                </#list>
                                <#assign angle = 0>
                                <#assign r = 0>
                                <#list languageRatioByMyReposCommit as langRatio >
                                    <#assign r = angle + langRatio.ratio / 2>
                                    <#assign angle = angle + langRatio.ratio>
                                    <#assign ratio = langRatio.ratio>
                                    <#assign name = langRatio.language.name>
                                    <#if langRatio?is_last && angle lt 1>
                                        <#assign r = angle + (1- angle) / 2>
                                        <#assign ratio = (1- angle)>
                                        <#assign name = "Other">
                                    </#if>
                                    <#if ratio gt 0.002>
                                        <text class="pie-text-percent" data-angle="${(360 * r - 90)}"
                                              data-name="${name}"
                                              fill="#ffffff"
                                              transform="rotate(90, 142.5,142.5)">${ratio * 100}%
                                        </text>
                                    </#if>
                                </#list>
                            </svg>
                        </div>
                    </div>
                </div>
                <div class="profile-chart-2">
                    <div class="profile-chart-detail">
                        <p class="content-title">My Stared Repos per Language</p>
                        <div class="pie" id="languageRatioByStarRepos">
                            <svg class="pie-svg" onload="calculateXY(this, 'languageRatioByStarRepos')">
                                <#assign r = 0>
                                <#assign bgColor = "#C67FA5">
                                <#list languageRatioByStarRepos as langRatio >
                                    <#if langRatio?index == 1 || langRatio?index == 4>
                                        <#assign bgColor = "#2A3139">
                                    </#if>

                                    <#if langRatio?index == 3 || langRatio?index == 6>
                                        <#assign bgColor = "#424D55">
                                    </#if>

                                    <#if langRatio?index == 2 || langRatio?index == 5>
                                        <#assign bgColor = "#303840">
                                    </#if>


                                    <#assign rotate = 360 * r>
                                    <#assign start = 700*langRatio.ratio>
                                    <#assign end = 700*(1-langRatio.ratio)>

                                    <#if langRatio?is_last>
                                        <#assign start = 700*((1-r))>
                                        <#assign end = 700*(r)>
                                    </#if>
                                    <#if r lt 0.99>
                                        <circle class="pie-1" r="111.5" cx="142.5" cy="142.5" stroke="${bgColor}"
                                                stroke-dasharray="${start} ${end}"
                                                transform="rotate(${rotate}, 142.5,142.5)"
                                                onmouseover="displayTip('languageRatioByStarRepos', ${langRatio?index})"></circle>
                                    </#if>
                                    <#assign r = r + langRatio.ratio>
                                </#list>
                                <#assign angle = 0>
                                <#assign r = 0>
                                <#list languageRatioByStarRepos as langRatio >
                                    <#assign r = angle + langRatio.ratio / 2>
                                    <#assign angle = angle + langRatio.ratio>
                                    <#assign ratio = langRatio.ratio>
                                    <#assign name = langRatio.language.name>
                                    <#if langRatio?is_last && angle lt 1>
                                        <#assign r = angle + (1- angle) / 2>
                                        <#assign ratio = (1- angle)>
                                        <#assign name = "Other">
                                    </#if>
                                    <#if ratio gt 0.002>
                                        <text class="pie-text-percent" data-angle="${(360 * r - 90)}"
                                              data-name="${name}"
                                              fill="#ffffff"
                                              transform="rotate(90, 142.5,142.5)">${ratio * 100}%
                                        </text>
                                    </#if>
                                </#list>

                            </svg>
                        </div>
                    </div>
                    <div class="profile-chart-detail">
                        <p class="content-title">Commits per My Repos</p>
                        <div class="pie" id="commitTopRepos">
                            <svg class="pie-svg" onload="calculateXY(this, 'commitTopRepos')">
                                <#assign r = 0>
                                <#assign bgColor = "#67A9E7">
                                <#list commitTopRepos as repos >
                                    <#if repos?index == 1 || repos?index == 4>
                                        <#assign bgColor = "#2A3139">
                                    </#if>

                                    <#if repos?index == 3 || repos?index == 6>
                                        <#assign bgColor = "#424D55">
                                    </#if>

                                    <#if repos?index == 2 || repos?index == 5>
                                        <#assign bgColor = "#303840">
                                    </#if>


                                    <#assign rotate = 360 * r>
                                    <#assign start = 700*repos.commitRadio>
                                    <#assign end = 700*(1-repos.commitRadio)>

                                    <#if repos?is_last>
                                        <#assign start = 700*((1-r))>
                                        <#assign end = 700*(r)>
                                    </#if>
                                    <#if r lt 0.99>
                                        <circle class="pie-1" r="111.5" cx="142.5" cy="142.5" stroke="${bgColor}"
                                                stroke-dasharray="${start} ${end}"
                                                transform="rotate(${rotate}, 142.5,142.5)"
                                                onmouseover="displayTip('commitTopRepos', ${repos?index})"></circle>
                                    </#if>
                                    <#assign r = r + repos.commitRadio>
                                </#list>
                                <#assign angle = 0>
                                <#assign r = 0>
                                <#list commitTopRepos as repos >
                                    <#assign r = angle + repos.commitRadio / 2>
                                    <#assign angle = angle + repos.commitRadio>
                                    <#assign ratio = repos.commitRadio>
                                    <#assign name = repos.nameWithOwner>
                                    <#if repos?is_last && angle lt 1>
                                        <#assign r = angle + (1- angle) / 2>
                                        <#assign ratio = (1- angle)>
                                        <#assign name = "Other">
                                    </#if>
                                    <#if ratio gt 0.002>
                                        <text class="pie-text-percent" data-angle="${(360 * r - 90)}" fill="#ffffff"
                                              data-name="${name?replace(login + "/", "")}"
                                              transform="rotate(90, 142.5,142.5)">${ratio * 100}%
                                        </text>
                                    </#if>
                                </#list>

                            </svg>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="error-page"></div>
<@layout.footer "profile-footer"/>
</body>
</html>