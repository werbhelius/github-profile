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
<div id="profile-page">
    <div class="profile-user-info-div">
        <div id="user-info" class="profile-user-info">
            <div id="user-detail" class="user-detail">
                <img class="user-img" src="https://avatars2.githubusercontent.com/u/12763277?s=460&v=4">
                <p class="user-name">werbhelius (wanbo)</p>
                <p class="user-bio">Less is More. https://t.me/werbhelius</p>
                <div class="flex">
                    <div class="user-friends">
                        <img src="../static/css/svg/follower.svg">
                        <div>
                            <p class="user-friends-count">366</p>
                            <p class="user-friends-name">Followers</p>
                        </div>
                    </div>
                    <div class="user-friends">
                        <img class="no-margin" src="../static/css/svg/following.svg">
                        <div>
                            <p class="user-friends-count">27</p>
                            <p class="user-friends-name">Following</p>
                        </div>
                    </div>
                    <div class="user-friends">
                        <img src="../static/css/svg/repositories.svg">
                        <div>
                            <p class="user-friends-count">366</p>
                            <p class="user-friends-name">Repositories</p>
                        </div>
                    </div>
                </div>
            </div>
            <div id="user-contributions" class="user-contributions">
                <p class="user-contributions-count">3,274 contributions in the last year</p>
                <div class="user-contributions-map">
                    <div >
                        <p class="user-contributions-max-count">100</p>
                        <p class="user-contributions-max-count">80</p>
                        <p class="user-contributions-max-count">60</p>
                        <p class="user-contributions-max-count">40</p>
                        <p class="user-contributions-max-count">20</p>
                        <p class="user-contributions-max-count">0</p>
                    </div>
                    <div class="user-contributions-chart">

                    </div>
                    <p class="user-contributions-year">2019</p>
                </div>
                <div class="user-contributions-month">
                    <p class="user-contributions-month-text">Feb</p>
                    <p class="user-contributions-month-text">Mar</p>
                    <p class="user-contributions-month-text">Apr</p>
                    <p class="user-contributions-month-text">May</p>
                    <p class="user-contributions-month-text">Jun</p>
                    <p class="user-contributions-month-text">Jul</p>
                    <p class="user-contributions-month-text">Aug</p>
                    <p class="user-contributions-month-text" >Sep</p>
                    <p class="user-contributions-month-text">Oct</p>
                    <p class="user-contributions-month-text">Nov</p>
                    <p class="user-contributions-month-text">Dec</p>
                    <p class="user-contributions-month-text">Jan</p>
                    <p class="user-contributions-month-text">Feb</p>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="error-page"></div>
<footer class="index-footer">
    <p class="index-footer-title">Licensed under the <a target="_blank"
                                                        href="https://github.com/werbhelius/github-profile"><span
            class="index-title-span">Apache 2 license</span></a></p>
    <p class="index-footer-title">Design & Develop <img src="../static/css/svg/heart.svg"> by <a target="_blank"
                                                                                                 href="https://about.me/werbhelius"><span
            class="index-title-span">werbhelius</span></a></p>
    <p class="index-footer-title">Source is on <a target="_blank"
                                                  href="https://github.com/werbhelius/github-profile"><span
            class="index-title-span">Github</span></a></p>
</footer>
</body>
</html>