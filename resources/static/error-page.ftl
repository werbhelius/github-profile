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
        <@layout.error/>
    </div>
    <@layout.footer "index-footer"/>
</div>
</body>
</html>