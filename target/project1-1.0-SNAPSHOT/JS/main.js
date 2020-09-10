const APP_VIEW = document.getElementById('app-view');

window.onload = function() {
    loadLogin();
}

function loadLogin() {

    console.log('in loadLogin()');
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            document.getElementById('login-message').style.display = "none";
        } 
    }


    xhr.open('GET', 'login.view', true)
    xhr.send();

    xhr.onerror = function() {
        document.getElementById('login-message').innerHTML = "Error";
    }
    
}

function loginButton() {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', './auth', true)

    let username = document.getElementById('login-username').value;
    let password = document.getElementById('login-password').value;

    let creds = {
        username: username,
        password: password
    };

    xhr.send(JSON.stringify(creds));

    if (xhr.status != 204) {
        document.getElementById('login-message').innerHTML = "Error: failed to login";
        document.getElementById('login-message').style.display  = "block";
    }
    
}

