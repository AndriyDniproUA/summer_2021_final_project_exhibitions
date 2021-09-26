
function validateRegistrationForm()
{
    let loginFld=document.getElementById("login");
    let loginVal=loginFld.value;

    let passwordFld=document.getElementById("password");
    let passwordVal=passwordFld.value;

    let password2Fld=document.getElementById("password2");
    let password2Val=password2Fld.value;

    let re = /^\w+$/;


    if(loginVal == "") {
        alert("Error: Login cannot be blank!");
        loginFld.focus();
        return false;
    }

    if(!re.test(loginVal)) {
        alert("Error: Login must contain only letters, numbers and underscores!");
        loginFld.focus();
        return false;
    }

    if(passwordVal != "" && passwordVal == password2Val) {
        if(!checkPassword(passwordVal)) {
            alert("The password you have entered is not valid!");
            alert("Valid password must contain at least 6 symbols including numbers, lower-case and upper-case English letters");
            passwordFld.focus();
            return false;
        }
    } else {
        alert("Error: Please check that you've entered and confirmed your password!");
        passwordFld.focus();
        return false;
    }
    return true;
}

function checkPassword(str)
{
    let re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
    return re.test(str);
}


function validateLoginForm(){
    let logField=document.getElementById("login");
    let logVal=logField.value;
    if( logVal == "" )  {
        alert("Login must be filled out!");
        logField.focus();
        return false;
    }

    let pasField=document.getElementById("password");
    let pasVal=pasField.value;
    if( pasVal == "" )  {
        alert("Password must be filled out!");
        pasField.focus();
        return false;
    }
}

function validateAddShowForm() {
    let subjField = document.getElementById("subject");
    let subjVal = subjField.value;
    if (subjVal == "") {
        alert("Subject must be filled out!");
        subjField.focus();
        return false;
    }
}



