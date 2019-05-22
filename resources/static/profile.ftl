<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>GitHub Profile Summary</title>
    <link rel="stylesheet" href="../static/css/style.css">
</head>
<body>
<header class="index-header">
    <div class="header-search">
        <img class="header-search-img" src="../static/css/svg/search.svg"/>
        <div id="userNameDiv" class="header-search-input-div">
            <input id='userNameInput' class="header-search-input" spellcheck="false"
                   placeholder="Input your GitHub Username Like 'werbhelius'"
                   onkeypress="checkName(event, this)"/>
        </div>
    </div>
    <p class="index-title-small">GitHub Profile <span class="index-title-span">Summary</span></p>
    <div class="header-search">
        <p class="header-search-share">share to</p>
        <div class="header-search-share-imag">
            <a target="_blank" style="margin-right: 16px" href="http://github.com/werbhelius"><img
                        src="../static/css/svg/share-twitter.svg"></a>
            <a target="_blank" style="margin-right: 16px" href="http://github.com/werbhelius"><img
                        src="../static/css/svg/share-fb.svg"></a>
            <a target="_blank" href="http://github.com/werbhelius"><img src="../static/css/svg/share-weibo.svg"></a>
        </div>
    </div>
</header>
<div id="profile-page" class="profile-page">
    <div class="profile-user-info-div">
        <div id="user-info" class="profile-user-info">
            <div id="user-detail" class="user-detail">
                <img class="user-img" src="${avatarUrl}">
                <p class="user-name">${login}(${name})</p>
                <p class="user-bio">${bio}</p>
                <div class="flex">
                    <div class="user-friends">
                        <img src="../static/css/svg/follower.svg">
                        <div>
                            <p class="user-friends-count">${followers}</p>
                            <p class="user-friends-name">Followers</p>
                        </div>
                    </div>
                    <div class="user-friends">
                        <img class="no-margin" src="../static/css/svg/following.svg">
                        <div>
                            <p class="user-friends-count">${following}</p>
                            <p class="user-friends-name">Following</p>
                        </div>
                    </div>
                    <div class="user-friends">
                        <img src="../static/css/svg/repositories.svg">
                        <div>
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
                                <img class="dot margin-3"
                                     style="margin-top: ${155 - (con.count/contributionsCounts.maxCount* 155)}px"
                                     src="../static/css/svg/dot.svg">
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
                    <p class="more-content">Email : ${email} </p>
                    <p class="more-content">Location : ${location}</p>
                    <p class="more-content">Website : ${websiteUrl}</p>
                </div>
            </div>
            <div class="profile-more">
                <p class="content-title" style="margin-top: 50px">Organizations</p>
                <div style="padding-top: 10px">
                    <div class="organizations-div">
                        <img class="organizations-img" src="https://avatars0.githubusercontent.com/u/1342004?s=70&v=4">
                        <p class="organizations-name">Google</p>
                    </div>
                    <div class="organizations-div">
                        <img class="organizations-img" src="https://avatars0.githubusercontent.com/u/1342004?s=70&v=4">
                        <p class="organizations-name">Google</p>
                    </div>
                    <div class="organizations-div">
                        <img class="organizations-img" src="https://avatars0.githubusercontent.com/u/1342004?s=70&v=4">
                        <p class="organizations-name">Google</p>
                    </div>
                    <div class="organizations-div">
                        <img class="organizations-img" src="https://avatars0.githubusercontent.com/u/1342004?s=70&v=4">
                        <p class="organizations-name">Google</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="profile-repos-block" style="margin-right: 81px">
            <p class="content-title">Pinned Repos</p>
            <div class="repos-bg">
                <#list pinnedRepos as repos>
                    <div class="repo">
                        <p class="repos-name">${repos.nameWithOwner}</p>
                        <p class="repos-desc">${repos.description}</p>
                        <div class="repos-star">
                            <div class="repos-lang" style="background-color:${repos.primaryLanguage.color};"></div>
                            <p class="repos-star-text">${repos.primaryLanguage.name}</p>
                            <img src="../static/css/svg/star.svg">
                            <p class="repos-star-text">${repos.stargazers.totalCount}</p>
                            <img src="../static/css/svg/fork.svg">
                            <p class="repos-star-text">${repos.forkCount}</p>
                        </div>
                    </div>
                    <#if !repos?is_last>
                        <div class="repos-line"></div>
                    </#if>
                </#list>
            </div>
        </div>
        <div class="profile-repos-block">
            <p class="content-title">Top Star Repos</p>
            <div class="repos-bg">
                <#list myRepos as repos>
                    <div class="repo">
                        <p class="repos-name">${repos.nameWithOwner}</p>
                        <p class="repos-desc">${repos.description}</p>
                        <div class="repos-star">
                            <div class="repos-lang" style="background-color:${repos.primaryLanguage.color}"></div>
                            <p class="repos-star-text">${repos.primaryLanguage.name}</p>
                            <img src="../static/css/svg/star.svg">
                            <p class="repos-star-text">${repos.stargazers.totalCount}</p>
                            <img src="../static/css/svg/fork.svg">
                            <p class="repos-star-text">${repos.forkCount}</p>
                        </div>
                    </div>
                    <#if !repos?is_last>
                        <div class="repos-line"></div>
                    </#if>
                </#list>
            </div>
        </div>
    </div>
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
                            <img style="margin-left: 14px" src="../static/css/svg/code-small.svg">
                            <p class="lang-rank-count">${rn.repository_count}</p>
                            <img style="margin-left: 14px" src="../static/css/svg/star-small.svg">
                            <p class="lang-rank-count">${rn.stars_count}</p>
                        </div>
                    </#if>
                </#list>
            </div>
        </div>
        <div class="profile-lang-world">
            <p class="content-title">Top Star Language</p>
            <div class="profile-lang-world-list">
                <#list rank as rn>
                    <div class="lang-world-bg">
                        <p class="lang-world-text">${rn.language}</p>
                        <div class="lang-world">
                            <img class="lang-img" src="../static/css/svg/city.svg">
                            <p class="lang-world-city-text">${city}</p>
                            <p class="lang-world-rank-text"><span class="lang-world-rank-text-high">${rn.city_rank}</span> / ${rn.city_count}</p>
                        </div>
                        <div class="lang-world">
                            <img class="lang-img" src="../static/css/svg/country.svg">
                            <p class="lang-world-city-text">${country}</p>
                            <p class="lang-world-rank-text"><span class="lang-world-rank-text-high">${rn.country_rank}</span> / ${rn.country_count}</p>
                        </div>
                        <div class="lang-world">
                            <img class="lang-img" src="../static/css/svg/world.svg">
                            <p class="lang-world-city-text">Worldwide</p>
                            <p class="lang-world-rank-text"><span class="lang-world-rank-text-high">${rn.world_rank}</span> / ${rn.world_count}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</div>
<div id="error-page"></div>
<!--<footer class="index-footer">-->
<!--<p class="index-footer-title">Licensed under the <a target="_blank"-->
<!--href="https://github.com/werbhelius/github-profile"><span-->
<!--class="index-title-span">Apache 2 license</span></a></p>-->
<!--<p class="index-footer-title">Design & Develop <img src="../static/css/svg/heart.svg"> by <a target="_blank"-->
<!--href="https://about.me/werbhelius"><span-->
<!--class="index-title-span">werbhelius</span></a></p>-->
<!--<p class="index-footer-title">Source is on <a target="_blank"-->
<!--href="https://github.com/werbhelius/github-profile"><span-->
<!--class="index-title-span">Github</span></a></p>-->
<!--</footer>-->
</body>
</html>