<#macro footer footerStyle>
    <footer id="footer" class=${footerStyle!"index-footer"}>
        <p class="index-footer-title">Licensed under the <a target="_blank"
                                                            href="https://github.com/werbhelius/github-profile"><span
                        class="index-title-span">Apache 2 license</span></a></p>
        <p class="index-footer-title">Design & Develop <img src="../static/svg/heart.svg"> by <a target="_blank"
                                                                                                 href="https://about.me/werbhelius"><span
                        class="index-title-span">werbhelius</span></a></p>
        <p class="index-footer-title">Source is on <a target="_blank"
                                                      href="https://github.com/werbhelius/github-profile"><span
                        class="index-title-span">Github</span></a></p>
    </footer>
</#macro>

<#macro header login>
    <header class="index-header">
        <div class="header-search" id="header-search">
            <img class="header-search-img" src="../static/svg/search.svg"/>
            <div id="userNameDiv" class="header-search-input-div">
                <input id='userNameInput' class="header-search-input" spellcheck="false"
                       placeholder="Input your GitHub Username Like 'werbhelius'"
                       onkeypress="checkName(event, this)"/>
            </div>
            <@loading "loading-div"/>
        </div>
        <p class="index-title-small" id="header-title">GitHub Profile <span class="index-title-span">Summary</span></p>
        <div class="header-search" id="header-search-share">
            <p class="header-search-share">share to</p>
            <div class="header-search-share-imag">
                <a target="_blank" style="margin-right: 16px" href="javascript:shareTweet('${login}')"><img
                            src="../static/svg/share-twitter.svg"></a>
                <a target="_blank" style="margin-right: 16px" href="javascript:shareFb('${login}')"><img
                            src="../static/svg/share-fb.svg"></a>
            </div>
        </div>
    </header>
</#macro>

<#macro loading loadingStyle>
    <div id="loading" class="${loadingStyle}" style="display: none">
        <div class="line-scale-pulse-out">
            <div></div>
            <div></div>
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>
</#macro>

<#macro head title>
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <meta name="theme-color" content="#22282e">
        <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
        <meta name="keywords" content="Github,profile,summary">
        <meta name="description" content="${title}">
        <meta name="author" content="wanbo">
        <link rel="shortcut icon" href="../static/svg/icon.png">
        <link rel="stylesheet" href="../static/css/style.css">
        <link rel="stylesheet" href="../static/css/loaders.min.css">
        <script type="text/javascript" src="../static/js/github-profile.js"></script>
    </head>
</#macro>

<#macro error>
    <div align="center">
        <p id="errorCode" class="error-code">${code}</p>
        <p class="error-message">${message}, Sorry </p>
        <button class="error-button" onclick="backToHome()">Back to the home page</button>
    </div>
</#macro>