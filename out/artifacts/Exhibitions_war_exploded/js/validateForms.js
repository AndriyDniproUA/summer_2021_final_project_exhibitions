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

    // var myField = document.getElementById("myNumber");
    // var value = myField.value;
    //
    // if( value == "" || isNaN(value) || value < 0 || value > 10)  {
    //     alert("Invalid input!");
    //     myField.focus();
    //     return false;
    // }
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



