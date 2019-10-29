package com.medbis.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private int patientId;

    private String pesel;

    private String surname;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String sex;

    private String community;

    private String zipCode;

    private String city;

    private String street;

    private String houseNumber;

    private String apartmentNumber;


}
