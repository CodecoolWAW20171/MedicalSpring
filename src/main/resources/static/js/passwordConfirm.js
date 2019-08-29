function isCorrectlyConfirmed() {
    var confirmedPassword = document.getElementById("confirmedPassword");
    var newPassword = document.getElementById("newPassword");
    var className = "";
    if(confirmedPassword.value === newPassword.value) {
        className = "form-control";
    }else {
       className = 'form-control is-invalid';
    }
    confirmedPassword.setAttribute("class", className);
}






