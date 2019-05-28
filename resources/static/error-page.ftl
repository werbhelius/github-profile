<#import "temp.ftl" as layout />
<!DOCTYPE html>
<html lang="en">
<head>
    <@layout.head "GitHub Profile Summary"/>
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