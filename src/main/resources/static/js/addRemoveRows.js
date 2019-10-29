function replaceTableMedicine(html) {
    // Replace the <fieldset id="medicineTable"> with a new one returned by server.
    $('#medicineTableF').replaceWith($(html));

    // Material Select Initialization
    $('.selectMedicine').materialSelect();
}

function addRowMedicine() {
    // e.preventDefault();
    var data = $('form').serialize();
    // Add parameter "addItem" to POSTed form data. Button's name and value is
    // POSTed only when clicked. Since "event.preventDefault();" prevents from
    // actual clicking the button, following line will add parameter to form
    // data.
    data += '&addMedicineRow=addMedicineRow';
    $.post('/patients/addNewPatient', data, replaceTableMedicine);
}

function dellRowMedicine(e) {
    // event.preventDefault();
    var data = $('form').serialize();
    // Add parameter and index of item that is going to be removed.
    data += '&removeMedicineRow=' + e.target.value;
    $.post('/patients/addNewPatient', data, replaceTableMedicine);
}


function replaceTableDisease(html) {
    // Replace the <fieldset id="medicineTable"> with a new one returned by server.
    $('#diseaseTableF').replaceWith($(html));

    // Material Select Initialization
    $('.selectDisease').materialSelect();
}


function addRowDisease() {
    // e.preventDefault();
    var data = $('form').serialize();
    // Add parameter "addItem" to POSTed form data. Button's name and value is
    // POSTed only when clicked. Since "event.preventDefault();" prevents from
    // actual clicking the button, following line will add parameter to form
    // data.
    data += '&addDiseaseRow=addDiseaseRow';
    $.post('/patients/addNewPatient', data, replaceTableDisease);
}

function dellRowDisease(e) {
    // event.preventDefault();
    var data = $('form').serialize();
    // Add parameter and index of item that is going to be removed.
    data += '&removeDiseaseRow=' + e.target.value;
    $.post('/patients/addNewPatient', data, replaceTableDisease);
}


function replaceTableTreatment(html) {
    // Replace the <fieldset id=""> with a new one returned by server.
    $('#treatmentTableF').replaceWith($(html));

    var noteList = document.querySelectorAll("textarea");
    for (var i = 0, len = noteList.length; i < len; i++) {
        if (noteList[i].value != "") {
            noteList[i].style.display = "";
        }
    }

    var badgeList = document.querySelectorAll(".noteBadge");
    for (var i = 0, len = badgeList.length; i < len; i++) {
        if (noteList[i].value != "") {
            badgeList[i].classList.add("badge-info");
        }
    }
}


function addTreatments(param) {
    var allTreatmentsInputs = document.querySelectorAll(".diffrentTreatment");
    for (var i = 0, len = allTreatmentsInputs.length; i < len; i++) {
        if (allTreatmentsInputs[i].value == param) {
            alert("Dodano już to świadczenie!");
            return;
        }
    }

    var data = $('form').serialize();
    data += '&addTreatment=' + param;
    $.post('/visits/{action}/addNewVisit', data, replaceTableTreatment);
}

function dellRowTreatment(e) {
    if ((confirm('Czy napewno usunąć świadczenie?'))) {
        var data = $('form').serialize();
        data += '&removeTreatment=' + e.target.value;
        $.post('/visits/{action}/addNewVisit', data, replaceTableTreatment);
    }

}

function showHideNote(e) {
    var note = document.getElementById("note" + e.target.value);
    var button = document.getElementById("button" + e.target.value);

    if (note.value != "") {
        if ((confirm('Czy napewno usunąć notatkę?'))) {
            note.style.display = note.style.display === 'none' ? '' : 'none';
            note.value = note.style.display === 'none' ? '' : note.value;
            button.classList.toggle("badge-info");
        }
    } else {
        note.style.display = note.style.display === 'none' ? '' : 'none';
        button.classList.toggle("badge-info");
    }
}

function codeAddress() {
    var noteList = document.querySelectorAll("textarea");
    for (var i = 0, len = noteList.length; i < len; i++) {
        if (noteList[i].value != "") {
            noteList[i].style.display = "";
        }
    }

    var badgeList = document.querySelectorAll(".noteBadge");
    for (var i = 0, len = badgeList.length; i < len; i++) {
        if (noteList[i].value != "") {
            badgeList[i].classList.add("badge-info");
        }
    }
}

window.onload = codeAddress;
