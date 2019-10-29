// $(document).ready(function () {

var startTime = Date.now();

$("#tablepage").dataTable({
    responsive: {
        details: {
            type: 'column'
        }
    },
    "bStateSave": true,
    "bSortClasses": false,
    paging: true,
    deferRender:    true,
    scrollY: "70vh",
    scroller:       true,
    "oLanguage": {
        "sEmptyTable": "Brak danych do wyświetlenia",
        "sInfo": "Znaleziono _TOTAL_ pacjentów (_START_ to _END_)",
        "sInfoEmpty": "Nie znaleziono pacjentów",
        "sInfoFiltered": " - z wszystkich _MAX_ pacjentów",
        "sLoadingRecords": "Proszę czekać - ładowanie bazy pacjentów...",
        "oLanguage": {
            "sProcessing": "Proszę czekać"
        }

    },

    ajax: {
        url: '/patientsList',
        dataSrc: ''
    },
    columns: [
        {data: 'patientId'},
        {data: 'patientId'},
        {data: 'pesel'},
        {data: 'surname'},
        {data: 'name'},
        {data: 'birthday'},
        {data: 'sex'},
        {data: 'zipCode'},
        {data: 'city'},
        {data: 'street'},
        {data: 'houseNumber'},
        {data: 'apartmentNumber'},

        {data: 'patientId'},
        {data: 'patientId'},
        {data: 'patientId'},
        // {data: 'patientId'},

    ],
    initComplete: function () {
        console.log('DT init complete in ', Date.now() - startTime + ' milliseconds.');
    },

    "columnDefs": [
        {
            className: 'control',
            orderable: false,
            targets:   0
        },
        {
            "targets": 0,
            "data": "patientId",
            "render": function (data, type, row, meta) {
                return '<span></span>'
            }
        },

        {"orderable": false, "targets": [0, 9, 10, 11, 12, 13]},

        {"searchable": false, "targets": [0,9, 10, 11, 12, 13]},

        {
            "targets": [-1, -2, -3],
            "createdCell": function (td, cellData, rowData, row, col) {
                $(td).css({'padding': '2px', 'text-align': 'center'})
            }
        },
        {
            "targets": [-4, -5, -6, -7],
            "createdCell": function (td, cellData, rowData, row, col) {
                $(td).css({'padding': '2px'})
            }
        },
        {
            "targets": [1],
            "createdCell": function (td, cellData, rowData, row, col) {
                $(td).css({'padding': '3px', 'text-align': 'center'})
            }
        },
        {
            "targets": 1,
            "data": "patientId",
            "render": function (data, type, row, meta) {
                return '<a href="/patients/showPatientDetails?patientIdDetails=' + data + '"><span><i class="fas fa-user"></i> </span> <span>' + data + '</span></a>'
            }
        },
        {
            "targets": 12,
            "data": "patientId",
            "render": function (data, type, row, meta) {
                return '<a href="mails/show-mail-form?patientId=' + data + '">  <span style="font-size: 1em; color: cornflowerblue;">  <i class="fas fa-envelope"></i> </span> </a>';
            }
        },
        {
            "targets": 13,
            "data": "patientId",
            "render": function (data, type, row, meta) {
                return '<a href="/visits/showFormForAddVisit?patientId=' + data + '">  <span style="font-size: 1em; color: lightseagreen;">  <i class="fas fa-user-nurse"></i> </span> </a>';
            }
        },
        {
            "targets": 14,
            "data": "patientId",
            "render": function (data, type, row, meta) {
                return '<a href="/patients/showFormForEditPatient?patientIdToEdit=' + data + '">  <span style="font-size: 1em; color: lightslategray;">  <i class="fas fa-user-edit"></i> </span> </a>';
            }
        },
        // {
        //     "targets": 14,
        //     "data": "patientId",
        //
        //     "render": function (data, type, row, meta) {
        //         return '<a  onclick="if(!(confirm(\'Czy jesteś pewien?\')))return false" href="/patients/delete?patientIdToDelete=' + data + '">  <span style="font-size: 1em; color: black;">  <i class="fas fa-trash-alt"></i> </span> </a>';
        //     }
        // }
    ]
});

$("#searchPatient").on("input change paste keyup", function (e) {
    e.preventDefault();
    $('#tablepage').DataTable().search($(this).val()).draw();
});

$("#resetInput").on("click", function (e) {
    e.preventDefault();
    $("#searchPatient").val("");
    $(".dataTables_filter input").val("")
    $('#tablepage').DataTable().search($("#searchPatient").val()).draw();
});

$("#searchPatient").val($(".dataTables_filter input").val());

// });