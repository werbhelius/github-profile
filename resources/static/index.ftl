<#import "temp.ftl" as layout />
<!DOCTYPE html>
<html lang="en">
<head>
    <@layout.head "GitHub Profile Summary"/>
</head>
<body>
<div style="height: 100%">
    <div id="index-page">
        <p class="index-title">GitHub Profile <span class="index-title-span">Summary</span></p>
        <div id="userNameDiv" class="index-input-div">
            <input id='userNameInput' class="index-input" spellcheck="false" placeholder="Input your GitHub Username Like 'werbhelius'" type="text" autocomplete="on"
                   onkeypress="checkName(event, this)"/>
        </div>
        <p class="index-find-me">Find me on&nbsp;&nbsp;&nbsp;
            <a target="_blank" href="http://github.com/werbhelius"><img src="../static/css/svg/github.svg"></a> ,&nbsp;&nbsp;&nbsp;
            <a target="_blank" href="https://twitter.com/WerbHelius"><img src="../static/css/svg/twitter.svg"></a> ,&nbsp;&nbsp;&nbsp;
            <a target="_blank" href="https://weibo.com/singerwannber"><img src="../static/css/svg/weibo.svg"></a> ,&nbsp;&nbsp;&nbsp;
            <a target="_blank" href="https://www.instagram.com/i.am.werb/"><img
                    src="../static/css/svg/ins.svg"></a>&nbsp;&nbsp;<span class="index-title-span">and</span>&nbsp;&nbsp;
            <a target="_blank" href="mailto:werbhelius@gmail.com"><img src="../static/css/svg/email.svg"></a> .</p>
        <@layout.loading "loading"/>
    </div>
    <div id="error-page"></div>
    <@layout.footer "index-footer"/>
</div>
</body>
</html>