
$(document).ready(function () {
    $("#searchPatient").val($(".dataTables_filter input").val());
});


$("#tablepage").dataTable({
    deferRender: true,
    scroller: true,
    "bStateSave": true,
    "bSortClasses": false,
    paging: false,
    scrollY: "70vh",
    fixedHeader: {
        header: true,
        footer: false
    },
    "oLanguage": {
        "sEmptyTable": "Brak danych do wyświetlenia",
        "sInfo": "Znaleziono _TOTAL_ pacjentów (_START_ to _END_)",
        "sInfoEmpty": "Nie znaleziono pacjentów",
        "sInfoFiltered": " - z wszystkich _MAX_ pacjentów",
        "sLoadingRecords": "Proszę czekać - ładowanie bazy pacjentów...",
        "oLanguage": {
            "sProcessing": "Proszę czekać"}

    },

    ajax: {
        url: '/patientsList',
        dataSrc: ''
    },
    columns: [
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
        {data: 'patientId'},

    ],


    "columnDefs": [
        {
            "targets": [-1, -2, -3, -4],
            "createdCell": function (td, cellData, rowData, row, col) {
                $(td).css({'padding': '2px', 'text-align': 'center'})
            }
        },
        {
            "targets": [-6, -7],
            "createdCell": function (td, cellData, rowData, row, col) {
                $(td).css({'padding': '2px'})
            }
        },
        {
            "targets": [0],
            "createdCell": function (td, cellData, rowData, row, col) {
                $(td).css({'padding': '3px', 'text-align': 'center'})
            }
        },
        {
            "targets": 0,
            "data": "patientId",
            "searchable": false,
            "render": function (data, type, row, meta) {
                return '<a href="/patients/showPatientDetails?patientIdDetails=' + data + '"><span><i class="fas fa-user"></i> </span> <span>'+ data +'</span></a>'
            }
        },
        {
            "targets": 11,
            "data": "patientId",
            "searchable": false,
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="/patients/showPatientDetails?patientIdDetails=' + data + '">  <span style="font-size: 1em; color: cornflowerblue;">  <i class="fas fa-envelope"></i> </span> </a>';
            }
        },
        {
            "targets": 12,
            "data": "patientId",
            "searchable": false,
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="/patients/showPatientDetails?patientIdDetails=' + data + '">  <span style="font-size: 1em; color: lightseagreen;">  <i class="fas fa-user-nurse"></i> </span> </a>';
            }
        },
        {
            "targets": 13,
            "data": "patientId",
            "searchable": false,
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="/patients/showPatientDetails?patientIdDetails=' + data + '">  <span style="font-size: 1em; color: lightslategray;">  <i class="fas fa-user-edit"></i> </span> </a>';
            }
        },
        {
            "targets": 14,
            "data": "patientId",
            "searchable": false,
            "orderable": false,
            "render": function (data, type, row, meta) {
                return '<a href="/patients/showPatientDetails?patientIdDetails=' + data + '">  <span style="font-size: 1em; color: black;">  <i class="fas fa-trash-alt"></i> </span> </a>';
            }
        }
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
