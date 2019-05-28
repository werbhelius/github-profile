<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>GitHub Profile Summary</title>
    <link rel="stylesheet" href="../static/css/style.css">
    <link rel="stylesheet" href="../static/css/loaders.min.css">
    <script type="text/javascript" src="../static/js/github-profile.js"></script>
</head>
<body>
<div style="height: 100%">
    <div id="error-page">
        <header class="index-header">
            <div class="header-search">
                <img class="header-search-img" src="../static/css/svg/search.svg"/>
                <div id="userNameDiv" class="header-search-input-div">
                    <input id='userNameInput' class="header-search-input" spellcheck="false"
                           placeholder="Input your GitHub Username Like 'werbhelius'"
                           onkeypress="checkName(event, this)"/>
                </div>
                <div id="loading" class="loading-div" style="display: none">
                    <div class="line-scale-pulse-out profile-loading">
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                    </div>
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
        <div align="center">
            <p id="errorCode" class="error-code">${code}</p>
            <p class="error-message">${message}, Sorry </p>
            <button class="error-button" onclick="backToHome()">Back to the home page</button>
        </div>
    </div>
    <footer id="footer" class="index-footer">
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
</div>
</body>
</html>