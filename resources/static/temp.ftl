<#macro footer footerStyle>
    <footer id="footer" class=${footerStyle!"index-footer"}>
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
</#macro>

<#macro header>
    <header class="index-header">
        <div class="header-search">
            <img class="header-search-img" src="../static/css/svg/search.svg"/>
            <div id="userNameDiv" class="header-search-input-div">
                <input id='userNameInput' class="header-search-input" spellcheck="false"
                       placeholder="Input your GitHub Username Like 'werbhelius'"
                       onkeypress="checkName(event, this)"/>
            </div>
            <@loading "loading-div"/>
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
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="../static/css/style.css">
    <link rel="stylesheet" href="../static/css/loaders.min.css">
    <script type="text/javascript" src="../static/js/github-profile.js"></script>
</#macro>