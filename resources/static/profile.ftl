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
</body>
</html>