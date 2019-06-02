var htmlWidth = document.documentElement.clientWidth || document.body.clientWidth;
var htmlDom = document.getElementsByTagName("html")[0];
if (htmlWidth > 1440) {
    htmlWidth = 1440
}
htmlDom.style.fontSize = htmlWidth / 1440 * 10 + "px";

function calculateXY(svg, div) {
    var texts = svg.getElementsByTagName("text");
    console.log(texts.length);
    for (var i = 0; i < texts.length; i++) {
        var text = texts[i];
        var angle = text.getAttribute("data-angle");

        var x = (111.5 * Math.cos(angle * 3.14 / 180) + 142.5 - 12).toFixed(1);
        var y = (111.5 * Math.sin(angle * 3.14 / 180) + 142.5 + 8).toFixed(1);

        text.setAttribute('x', x);
        text.setAttribute('y', y);

        var targetDiv = document.getElementById(div);
        var tipDiv = document.createElement("div");
        tipDiv.className = "tip";
        var p = document.createElement("p");
        p.className = "bubble";
        p.textContent = text.getAttribute("data-name");
        var triangleDiv = document.createElement("div");
        triangleDiv.className = "triangle";
        tipDiv.appendChild(p);
        tipDiv.appendChild(triangleDiv);
        targetDiv.appendChild(tipDiv);
        var width = tipDiv.offsetWidth;
        tipDiv.style.left = x - width / 2 + 8 + "px";
        tipDiv.style.top = y - 45 + "px";

        if (i <= 2) {
            tipDiv.classList.add("visible");
        } else {
            tipDiv.classList.add("hidden");
        }
    }
}

function displayTip(div, index) {
    var targetDiv = document.getElementById(div);
    var tips = targetDiv.getElementsByClassName("tip");
    for (var i = 0; i < tips.length; i++) {
        var tip = tips[i];
        if (i === index) {
            tip.classList.add("visible");
            tip.classList.remove("hidden");
        } else {
            tip.classList.add("hidden");
            tip.classList.remove("visible");
        }
    }
}

function checkName(e, input) {
    if (e.code !== "Enter") return;
    if (input.value === "") {
        input.classList.add("shake");
        setTimeout(function () {
            input.classList.remove("shake");
        }, 500);
    } else {
        requestName(input)
    }
}

function requestName(input) {
    input.disabled = "disabled";
    document.getElementById("loading").style.display = "block";
    var name = input.value;
    var ajaxObj = new XMLHttpRequest();
    ajaxObj.open('get', '/api/user?username=' + name);
    ajaxObj.setRequestHeader("client", "web");
    ajaxObj.send();
    ajaxObj.onreadystatechange = function () {
        if (ajaxObj.readyState !== 4) return;
        document.getElementById("loading").style.display = "none";
        input.disabled = "";
        if (ajaxObj.status >= 200 && ajaxObj.status < 400) {
            window.location = ajaxObj.responseText
        } else {
            document.getElementById("index-page").style.display = "none";
            var footer = document.getElementById("footer");
            footer.classList.remove("profile-footer");
            footer.classList.add("index-footer");
            var errorPage = document.getElementById("error-page");
            errorPage.innerHTML = ajaxObj.responseText;
            var errorCode = document.getElementById("errorCode");
            errorCode.classList.add("shake");
            setTimeout(function () {
                errorCode.classList.remove("shake");
            }, 500);
        }
    }
}

function backToHome() {
    window.location = "/"
}

function shareTweet(login) {
    var domain = document.domain;
    if (login === "") {
        window.open("https://twitter.com/intent/tweet?url=http://" + domain + "&text= See Your Github Profile Summary" + "&via=WerbHelius");
        return;
    }
    window.open("https://twitter.com/intent/tweet?url=http://" + domain + "/profile/" + login + "&text=" + login + "'s Github Profile Summary" + "&via=WerbHelius")
}

function shareFb(login) {
    var domain = document.domain;
    if (login === "") {
        window.open("https://twitter.com/intent/tweet?url=http://" + domain + "&text= See Your Github Profile Summary" + "&via=WerbHelius");
        return;
    }
    window.open("https://facebook.com/sharer.php?u=http://" + domain + "/profile/" + login + "&text=" + login + "'s Github Profile Summary");
}