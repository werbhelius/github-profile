<#import "temp.ftl" as layout />
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
        <@layout.header/>
        <div align="center">
            <p id="errorCode" class="error-code">${code}</p>
            <p class="error-message">${message}, Sorry </p>
            <button class="error-button" onclick="backToHome()">Back to the home page</button>
        </div>
    </div>
    <@layout.footer "index-footer"/>
</div>
</body>
</html>