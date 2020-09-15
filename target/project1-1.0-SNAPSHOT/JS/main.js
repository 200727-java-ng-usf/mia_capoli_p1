const APP_VIEW = document.getElementById('app-view');

window.onload = function() {
    loadLogin();
    document.getElementById("toLogin").addEventListener('click', loadLogin);
    document.getElementById("toRegister").addEventListener('click', loadRegister);
    document.getElementById("toLogout").addEventListener('click', logout);
    document.getElementById("toHome").addEventListener('click', loadHome);
    document.getElementById("toUpdate").addEventListener('click', loadUpdate);
    document.getElementById("toDelete").addEventListener('click', loadDelete);
}

function loadButtons() {
    if (document.getElementById("toLogin")) {
        document.getElementById("toLogin").addEventListener('click', loadLogin);
    }
    if (document.getElementById("toRegister")) {
        document.getElementById("toRegister").addEventListener('click', loadRegister);
    }
    if (document.getElementById("toLogout")) {
        document.getElementById("toLogout").addEventListener('click', logout);
    }
    if (document.getElementById("toHome")) {
        document.getElementById("toHome").addEventListener('click', loadHome);
    }
    if (document.getElementById("toUpdate")) {
        document.getElementById("toUpdate").addEventListener('click', loadUpdate);
    }
    if (document.getElementById("toUpdateReimb")) {
        document.getElementById("toUpdateReimb").addEventListener('click', loadUpdateReimb);
    }
    if (document.getElementById("toDelete")) {
        document.getElementById("toDelete").addEventListener('click', loadDelete);
    }
    
}


function loadLogin() {

    console.log('in loadLogin()');
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureLoginView();
        }
    }


    xhr.open('GET', 'login.view', true)
    xhr.send();


}

function loadRegister() {
    console.log('in loadRegister()');
    let xhr = new XMLHttpRequest();


    xhr.open('GET', 'register.view', true)
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureRegisterView();
         }

    }

}

function loadHome() {
    console.log('in loadHome()');

    if (!localStorage.getItem('authUser')) {
        console.log("no user logged in, nav to login screen");
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();
    let authUser = JSON.parse(localStorage.getItem('authUser'));

    if (authUser.role == 'Admin' || authUser.role == 'admin') {
        xhr.open('GET', 'adminhome.view', true);
        xhr.send();

        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                APP_VIEW.innerHTML = xhr.responseText;
                configureHomeView();
             }
        }
    } else if (authUser.role == 'FinanceMan' || authUser.role == 'financeman') {
        xhr.open('GET', 'financehome.view', true);
        xhr.send();

        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                APP_VIEW.innerHTML = xhr.responseText;
                configureFinanceHomeView();
             }
    
        }
    } else {
        xhr.open('GET', 'home.view', true);
        xhr.send();
    }


    }


function loadEmployeeHome() {
    console.log('in loadHome()');

    if (!localStorage.getItem('authUser')) {
        console.log("no user logged in, nav to login screen");
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    if (authUser.role == 'Admin' || authUser.role == 'admin') {
        xhr.open('GET', 'adminhome.view', true);
        xhr.send();
    } else {
        xhr.open('GET', 'home.view', true);
        xhr.send();
    }


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureHomeView();
         }

    }

}

function loadUpdate() {
    if (!localStorage.getItem('authUser')) {
        console.log("no user logged in, nav to login screen");
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    if (authUser.role == 'Admin' || authUser.role == 'admin') {
        xhr.open('GET', 'update.view', true);
        xhr.send();

        //TODO FIX to make it return bad things when trying to delete a user as a non admin
    } else {
        xhr.open('GET', 'home.view', true);
        xhr.send();
    }


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUpdateView();
         }

    }
}

function loadUpdate() {
    if (!localStorage.getItem('authUser')) {
        console.log("no user logged in, nav to login screen");
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    if (authUser.role == 'FinanceMan' || authUser.role == 'admin') {
        xhr.open('GET', 'update.view', true);
        xhr.send();

        //TODO FIX to make it return bad things when trying to delete a user as a non admin
    } else {
        xhr.open('GET', 'home.view', true);
        xhr.send();
    }


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUpdateView();
         }

    }
}

function loadUpdateReimb() {
    if (!localStorage.getItem('authUser')) {
        console.log("no user logged in, nav to login screen");
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    if (authUser.role == 'FinanceMan' || authUser.role == 'admin') {
        xhr.open('GET', 'updatereimb.view', true);
        xhr.send();

        //TODO FIX to make it return bad things when trying to delete a user as a non admin
    } else {
        xhr.open('GET', 'home.view', true);
        xhr.send();
    }


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUpdateReimbView();
         }

    }
}


function loadDelete() {
    if (!localStorage.getItem('authUser')) {
        console.log("no user logged in, nav to login screen");
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    if (authUser.role == 'Admin' || authUser.role == 'admin') {
        xhr.open('GET', 'delete.view', true);
        xhr.send();

        //TODO FIX to make it return bad things when trying to delete a user as a non admin
    } else {
        xhr.open('GET', 'home.view', true);
        xhr.send();
    }


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureDeleteView();
         }

    }

}

//---------------------------------------------------------------------------------

function configureLoginView() {

    console.log('in configureLoginView()');

    document.getElementById('login-message').setAttribute('hidden', true);
    document.getElementById('login-button-container').addEventListener('mouseover', validateLoginForm);
    document.getElementById('login').addEventListener('click', login)
}

function configureRegisterView() {
    loadButtons();

    console.log('in configureRegisterView()');

    document.getElementById('reg-message').setAttribute('hidden', true);

    document.getElementById('reg-username').addEventListener('blur', isUsernameAvailable);
    document.getElementById('email').addEventListener('blur', isEmailAvailable);

    document.getElementById('register').setAttribute('disabled', true);
    document.getElementById('reg-button-container').addEventListener('mouseover', validateRegisterForm);
    document.getElementById('register').addEventListener('click', register);
}

function configureHomeView() {
    loadButtons();
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;

    let xhrusers = new XMLHttpRequest();

    xhrusers.open('GET', 'users', true);
    xhrusers.send();


    xhrusers.onreadystatechange = function() {
        if (xhrusers.readyState == 4 && xhrusers.status == 200) {
            var users = JSON.parse(xhrusers.responseText);
         }

    

    let table = document.getElementById("adminTable");
	table.removeChild(document.getElementById("list"));
	let body = document.createElement("tbody");
	body.setAttribute("id", "list");
	table.appendChild(body);


    for(let i = 0 ; i < users.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + users[i].id + "</td>" +
		"<td>" + users[i].username + "</td>" +
		"<td>" + users[i].firstName + "</td>" +
		"<td>" + users[i].lastName + "</td>" +
		"<td>" + users[i].email + "</td>" ;

        body.appendChild(newRow);
    }
}
}

function configureDeleteView() {
    loadButtons();

    document.getElementById("delete-button-container").addEventListener('mouseover', validateDeleteForm);
    document.getElementById('deleteU').addEventListener('click', deleteUser)
    let authUser = JSON.parse(localStorage.getItem('authUser'));

    let xhrusers = new XMLHttpRequest();

    xhrusers.open('GET', 'users', true);
    xhrusers.send();


    xhrusers.onreadystatechange = function() {
        if (xhrusers.readyState == 4 && xhrusers.status == 200) {
            var users = JSON.parse(xhrusers.responseText);
         }

    

    let table = document.getElementById("adminTable");
	table.removeChild(document.getElementById("list"));
	let body = document.createElement("tbody");
	body.setAttribute("id", "list");
	table.appendChild(body);


    for(let i = 0 ; i < users.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + users[i].id + "</td>" +
		"<td>" + users[i].username + "</td>" +
		"<td>" + users[i].firstName + "</td>" +
		"<td>" + users[i].lastName + "</td>" +
		"<td>" + users[i].email + "</td>" ;

        body.appendChild(newRow);
    }
}
}

function configureUpdateView() {
    loadButtons();

    document.getElementById("update-button-container").addEventListener('mouseover', validateUpdateForm);
    document.getElementById('updateButton').addEventListener('click', updateUser)
    let authUser = JSON.parse(localStorage.getItem('authUser'));

    let xhrusers = new XMLHttpRequest();

    xhrusers.open('GET', 'users', true);
    xhrusers.send();


    xhrusers.onreadystatechange = function() {
        if (xhrusers.readyState == 4 && xhrusers.status == 200) {
            var users = JSON.parse(xhrusers.responseText);
         }

    

    let table = document.getElementById("adminTable");
	table.removeChild(document.getElementById("list"));
	let body = document.createElement("tbody");
	body.setAttribute("id", "list");
	table.appendChild(body);


    for(let i = 0 ; i < users.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + users[i].id + "</td>" +
		"<td>" + users[i].firstName + "</td>" +
        "<td>" + users[i].lastName + "</td>" +
        "<td>" + users[i].username + "</td>" +
		"<td>" + users[i].email + "</td>" ;

        body.appendChild(newRow);
    }
}
}


function configureUpdateReimbView() {
    loadButtons();

    document.getElementById("update-reimb-button-container").addEventListener('mouseover', validateReimbUpdateForm);
    document.getElementById('updateReimbButton').addEventListener('click', approveReimb)
    let authUser = JSON.parse(localStorage.getItem('authUser'));

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbs', true);
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var reimbs = JSON.parse(xhr.responseText);
         }

    

    let table = document.getElementById("financeTable");
    table.removeChild(document.getElementById("list"));
    let body = document.createElement("tbody");
    body.setAttribute("id", "list");
    table.appendChild(body);


    for(let i = 0 ; i < reimbs.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + reimbs[i].reimb_id + "</td>" +
        "<td>" + USD.format(reimbs[i].amount) + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
        "<td>" + reimbs[i].description + "</td>" + 
        "<td>" + reimbs[i].author_id + "</td>" +
        "<td>" + reimbs[i].resolver_id + "</td>" +
        "<td>" + reimbs[i].reimb_status + "</td>" +
        "<td>" + reimbs[i].reimb_type + "</td>";

        body.appendChild(newRow);
    }
}
}

function configureFinanceHomeView() {
    loadButtons();
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbs', true);
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var reimbs = JSON.parse(xhr.responseText);
         }

    

    let table = document.getElementById("financeTable");
    table.removeChild(document.getElementById("list"));
    let body = document.createElement("tbody");
    body.setAttribute("id", "list");
    table.appendChild(body);


    for(let i = 0 ; i < reimbs.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + reimbs[i].reimb_id + "</td>" +
        "<td>" + USD.format(reimbs[i].amount) + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
        "<td>" + reimbs[i].description + "</td>" + 
        "<td>" + reimbs[i].author_id + "</td>" +
        "<td>" + reimbs[i].resolver_id + "</td>" +
        "<td>" + reimbs[i].reimb_status + "</td>" +
        "<td>" + reimbs[i].reimb_type + "</td>";

        body.appendChild(newRow);
    }
}
}



function sortByLodging() {
    
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbslodging', true);
    xhr.send();


     xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var reimbs = JSON.parse(xhr.responseText);
             }
    
        
    
        let table = document.getElementById("financeTable");
        if (table.childElementCount != 0) {
            table.removeChild(document.getElementById("list"));
        } 
        let body = document.createElement("tbody");
        body.setAttribute("id", "list");
        table.appendChild(body);
    
    
        for(let i = 0 ; i < reimbs.length; i++){
    
            let newRow = document.createElement("tr");
    
            newRow.innerHTML = 
            "<td>" + reimbs[i].reimb_id + "</td>" +
            "<td>" + USD.format(reimbs[i].amount) + "</td>" +
            "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
            "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
            "<td>" + reimbs[i].description + "</td>" + 
            "<td>" + reimbs[i].author_id + "</td>" +
            "<td>" + reimbs[i].resolver_id + "</td>" +
            "<td>" + reimbs[i].reimb_status + "</td>" +
            "<td>" + reimbs[i].reimb_type + "</td>";
    
            body.appendChild(newRow);
        }
    }
}

function sortByTravel() {

    
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbstravel', true);
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var reimbs = JSON.parse(xhr.responseText);
         }

    

    let table = document.getElementById("financeTable");
    if (table.childElementCount != 0) {
        table.removeChild(document.getElementById("list"));
    } 
    let body = document.createElement("tbody");
    body.setAttribute("id", "list");
    table.appendChild(body);


    for(let i = 0 ; i < reimbs.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + reimbs[i].reimb_id + "</td>" +
        "<td>" + USD.format(reimbs[i].amount) + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
        "<td>" + reimbs[i].description + "</td>" + 
        "<td>" + reimbs[i].author_id + "</td>" +
        "<td>" + reimbs[i].resolver_id + "</td>" +
        "<td>" + reimbs[i].reimb_status + "</td>" +
        "<td>" + reimbs[i].reimb_type + "</td>";

        body.appendChild(newRow);
    }
}
}

function sortByFood() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbsfood', true);
    xhr.send();


     xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var reimbs = JSON.parse(xhr.responseText);
             }
    
        
    
        let table = document.getElementById("financeTable");
        if (table.childElementCount != 0) {
            table.removeChild(document.getElementById("list"));
        } 
        let body = document.createElement("tbody");
        body.setAttribute("id", "list");
        table.appendChild(body);
    
        reimbs.forEach(foods)
    
        function foods(reimb) {
        // for(let i = 0 ; i < reimbs.length; i++){
    
            let newRow = document.createElement("tr");
    
            newRow.innerHTML = 
            "<td>" + reimb.reimb_id + "</td>" +
            "<td>" + USD.format(reimb.amount) + "</td>" +
            "<td>" + new Date(parseInt(reimb.submitted)).toLocaleDateString() + "</td>" +
            "<td>" + new Date(parseInt(reimb.resolved)).toLocaleDateString() + "</td>" +
            "<td>" + reimb.description + "</td>" + 
            "<td>" + reimb.author_id + "</td>" +
            "<td>" + reimb.resolver_id + "</td>" +
            "<td>" + reimb.reimb_status + "</td>" +
            "<td>" + reimb.reimb_type + "</td>";
    
            body.appendChild(newRow);
        }
    }
}


function sortByOther() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbsother', true);
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var reimbs = JSON.parse(xhr.responseText);
         }

    

    let table = document.getElementById("financeTable");
    if (table.childElementCount != 0) {
        table.removeChild(document.getElementById("list"));
    } 
    let body = document.createElement("tbody");
    body.setAttribute("id", "list");
    table.appendChild(body);


    for(let i = 0 ; i < reimbs.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + reimbs[i].reimb_id + "</td>" +
        "<td>" + USD.format(reimbs[i].amount) + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
        "<td>" + reimbs[i].description + "</td>" + 
        "<td>" + reimbs[i].author_id + "</td>" +
        "<td>" + reimbs[i].resolver_id + "</td>" +
        "<td>" + reimbs[i].reimb_status + "</td>" +
        "<td>" + reimbs[i].reimb_type + "</td>";

        body.appendChild(newRow);
    }
}
}

function sortByPending() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbspending', true);
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var reimbs = JSON.parse(xhr.responseText);
         }

    

    let table = document.getElementById("financeTable");
    if (table.childElementCount != 0) {
        table.removeChild(document.getElementById("list"));
    } 
    let body = document.createElement("tbody");
    body.setAttribute("id", "list");
    table.appendChild(body);


    for(let i = 0 ; i < reimbs.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + reimbs[i].reimb_id + "</td>" +
        "<td>" + USD.format(reimbs[i].amount) + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
        "<td>" + reimbs[i].description + "</td>" + 
        "<td>" + reimbs[i].author_id + "</td>" +
        "<td>" + reimbs[i].resolver_id + "</td>" +
        "<td>" + reimbs[i].reimb_status + "</td>" +
        "<td>" + reimbs[i].reimb_type + "</td>";

        body.appendChild(newRow);
    }
}
}

function sortByApproved() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbsapproved', true);
    xhr.send();


     xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var reimbs = JSON.parse(xhr.responseText);
             }
    
        
    
        let table = document.getElementById("financeTable");
        if (table.childElementCount != 0) {
            table.removeChild(document.getElementById("list"));
        } 
        let body = document.createElement("tbody");
        body.setAttribute("id", "list");
        table.appendChild(body);
    
    
        for(let i = 0 ; i < reimbs.length; i++){
    
            let newRow = document.createElement("tr");
    
            newRow.innerHTML = 
            "<td>" + reimbs[i].reimb_id + "</td>" +
            "<td>" + USD.format(reimbs[i].amount) + "</td>" +
            "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
            "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
            "<td>" + reimbs[i].description + "</td>" + 
            "<td>" + reimbs[i].author_id + "</td>" +
            "<td>" + reimbs[i].resolver_id + "</td>" +
            "<td>" + reimbs[i].reimb_status + "</td>" +
            "<td>" + reimbs[i].reimb_type + "</td>";
    
            body.appendChild(newRow);
        }
    }
}

function sortByDenied() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbsdenied', true);
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var reimbs = JSON.parse(xhr.responseText);
         }

    

    let table = document.getElementById("financeTable");
    if (table.childElementCount != 0) {
        table.removeChild(document.getElementById("list"));
    } 
    let body = document.createElement("tbody");
    body.setAttribute("id", "list");
    table.appendChild(body);


    for(let i = 0 ; i < reimbs.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + reimbs[i].reimb_id + "</td>" +
        "<td>" + USD.format(reimbs[i].amount) + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
        "<td>" + reimbs[i].description + "</td>" + 
        "<td>" + reimbs[i].author_id + "</td>" +
        "<td>" + reimbs[i].resolver_id + "</td>" +
        "<td>" + reimbs[i].reimb_status + "</td>" +
        "<td>" + reimbs[i].reimb_type + "</td>";

        body.appendChild(newRow);
    }
}
}


function sortByAll() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbs', true);
    xhr.send();


    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var reimbs = JSON.parse(xhr.responseText);
         }

    

    let table = document.getElementById("financeTable");
    if (table.childElementCount != 0) {
        table.removeChild(document.getElementById("list"));
    } 
    let body = document.createElement("tbody");
    body.setAttribute("id", "list");
    table.appendChild(body);


    for(let i = 0 ; i < reimbs.length; i++){

        let newRow = document.createElement("tr");

        newRow.innerHTML = 
        "<td>" + reimbs[i].reimb_id + "</td>" +
        "<td>" + USD.format(reimbs[i].amount) + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].submitted)).toLocaleDateString() + "</td>" +
        "<td>" + new Date(parseInt(reimbs[i].resolved)).toLocaleDateString() + "</td>" +
        "<td>" + reimbs[i].description + "</td>" + 
        "<td>" + reimbs[i].author_id + "</td>" +
        "<td>" + reimbs[i].resolver_id + "</td>" +
        "<td>" + reimbs[i].reimb_status + "</td>" +
        "<td>" + reimbs[i].reimb_type + "</td>";

        body.appendChild(newRow);
    }
}
}



const USD = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2
  })

//--------------------------------------------------------------


function login() {
    console.log('in login()');

    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;

    let creds = {
        username: un,
        password: pw
    };

    let xhr = new XMLHttpRequest();
    xhr.open('POST', './auth');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(creds));

    xhr.onreadystatechange = function() {
        if  (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('login-message').setAttribute('hidden', true);
            console.log('SUCCESS!')
            localStorage.setItem('authUser', xhr.responseText);
            loadHome();

        } else if (xhr.readyState == 4 && xhr.status == 401) {
            document.getElementById('login-message').removeAttribute('hidden');

            let err = JSON.parse(xhr.responseText);
            document.getElementById('login-message').innerText = err.message;
        }
    }

}


function register() {
    console.log('in register()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;

        let creds = {
            username: un,
            password: pw,
            firstName: fn,
            lastName: ln,
            email: email
        };

    let xhr = new XMLHttpRequest();
    xhr.open('POST', './users');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(creds));

    xhr.onreadystatechange = function() {
        if  (xhr.readyState == 4 && xhr.status == 201) {
            document.getElementById('reg-message').setAttribute('hidden', true);
            console.log('SUCCESS!')


        } else if (xhr.readyState == 4 && xhr.status == 400) {
            document.getElementById('reg-message').removeAttribute('hidden');

            let err = JSON.parse(xhr.responseText);
            document.getElementById('reg-message').innerText = err.message;
        }
    }

}


function deleteUser() {

    let id = document.getElementById('deleteU').value;
    

        let creds = {
            id: id
        }


    let xhr = new XMLHttpRequest();
    xhr.open('POST', './deleteuser');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(id);

    xhr.onreadystatechange = function() {
        if  (xhr.readyState == 4 && xhr.status == 201) {
            document.getElementById('reg-message').setAttribute('hidden', true);
            console.log('SUCCESS!')


        } else if (xhr.readyState == 4 && xhr.status == 400) {
            document.getElementById('reg-message').removeAttribute('hidden');

            let err = JSON.parse(xhr.responseText);
            document.getElementById('reg-message').innerText = err.message;
        }
    }
}


function updateUser() {
    
        let fn = document.getElementById('fn').value;
        let ln = document.getElementById('ln').value;
        let email = document.getElementById('email').value;
        let un = document.getElementById('reg-username').value;
        let pw = document.getElementById('reg-password').value;
    
            let creds = {
                username: un,
                password: pw,
                firstName: fn,
                lastName: ln,
                email: email
            };
    
        let xhr = new XMLHttpRequest();
        xhr.open('POST', './updateuser');
        xhr.setRequestHeader('Content-type', 'application/json');
        xhr.send(JSON.stringify(creds));
    
        xhr.onreadystatechange = function() {
            if  (xhr.readyState == 4 && xhr.status == 201) {
                document.getElementById('reg-message').setAttribute('hidden', true);
                console.log('SUCCESS!')
    
    
            } else if (xhr.readyState == 4 && xhr.status == 400) {
                document.getElementById('reg-message').removeAttribute('hidden');
    
                let err = JSON.parse(xhr.responseText);
                document.getElementById('reg-message').innerText = err.message;
            }
        }
    
}


function approveReimb() {
    
    let approve = document.getElementById('approve');
    let denied = document.getElementById('deny');
    let reimbId = document.getElementById('id').value;

    let xhr = new XMLHttpRequest();
    xhr.open('POST', './updatereimb');
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    
    if (approve.checked) {
        xhr.send(`id=${reimbId}&status=approved`);
    } else if (denied.checked) {
        xhr.send(`id=${reimbId}&status=denied`);
    }

    xhr.onreadystatechange = function() {
        if  (xhr.readyState == 4 && xhr.status == 201) {
            document.getElementById('reg-message').setAttribute('hidden', true);
            console.log('SUCCESS!')


        } else if (xhr.readyState == 4 && xhr.status == 400) {
            document.getElementById('reg-message').removeAttribute('hidden');

            let err = JSON.parse(xhr.responseText);
            document.getElementById('reg-message').innerText = err.message;
        }
    }

}



function isUsernameAvailable() {

    console.log('in isUsernameAvailable()');

    let username = document.getElementById('reg-username').value;

    if(!username) {
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'username.validate');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(username));

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 204) {
            console.log('Provided username is available!');
            document.getElementById('reg-message').setAttribute('hidden', true);
        } else if (xhr.readyState == 4 && xhr.status == 409 ) {
            document.getElementById('reg-message').removeAttribute('hidden')
            document.getElementById('reg-message').innerText = 'The provided username is already taken!';
            document.getElementById('register').setAttribute('disabled', true);
        }
    }

}

function logout() {

    console.log('in logout()');

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'auth');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 204) {
            console.log('logout successful!');
            localStorage.removeItem('authUser');
            loadLogin();
        }
    }
}


function isEmailAvailable() {

    console.log('in isEmailAvailable()');

    let email = document.getElementById('email').value;

    if(!email) {
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'email.validate');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(email));

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 204) {
            console.log('Provided email is available!');
            document.getElementById('reg-message').setAttribute('hidden', true);
        } else if (xhr.readyState == 4 && xhr.status == 409) {
            document.getElementById('reg-message').removeAttribute('hidden');
            document.getElementById('reg-message').innerText = 'The provided email address is already taken!';
            document.getElementById('register').setAttribute('disabled', true);
        }
    }
}

function validateLoginForm() {

    console.log('in validateLoginForm()');

    let msg = document.getElementById('login-message').innerText;

    if (msg == 'User authentication failed!') {
        return;
    }

    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;

    if (!un || !pw) {
        document.getElementById('login-message').removeAttribute('hidden');
        document.getElementById('login-message').innerText = 'You must provided values for all fields in the form!'
        document.getElementById('login').setAttribute('disabled', true);
    } else {
        document.getElementById('login').removeAttribute('disabled');
        document.getElementById('login-message').setAttribute('hidden', true);
    }

}

function validateRegisterForm() {

    console.log('in validateRegisterForm()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;

    if (!fn || !ln || !email || !un || !pw) {
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must provided values for all fields in the form!'
        document.getElementById('register').setAttribute('disabled', true);
    } else {
        document.getElementById('register').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }
}

function validateDeleteForm() {

    console.log('in validateDeleteForm()');

    let id = document.getElementById('deleteForm').value;

    if (!id) {
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must provide an Id!'
        document.getElementById('deleteU').setAttribute('disabled', true);
    } else {
        document.getElementById('deleteU').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }
}

function validateUpdateForm() {

    console.log('in validateUpdateForm()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;


    if (!fn || !ln || !email || !un || !pw) {
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must provide all values!'
        document.getElementById('updateButton').setAttribute('disabled', true);
    } else {
        document.getElementById('updateButton').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }
}

function validateReimbUpdateForm() {

    console.log('in validateUpdateForm()');

    let id1 = document.getElementById('approve').checked;
    let id2 = document.getElementById('deny').checked;


    if (!id1 && !id2) {
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must check a status!'
        document.getElementById('updateReimbButton').setAttribute('disabled', true);
    } else {
        document.getElementById('updateReimbButton').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }
}

